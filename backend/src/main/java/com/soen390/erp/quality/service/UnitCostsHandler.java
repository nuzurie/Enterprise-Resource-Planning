package com.soen390.erp.quality.service;

import com.soen390.erp.email.model.EmailToSend;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitCostsHandler extends QualityDataHandler{


    @Override
    public void handle(List<String> currentMonthRecord)
    {
        String unitCosts = currentMonthRecord.get(3);

        if (Integer.parseInt(unitCosts) > 160)
        {
            String message = "Warning, unit costs exceed $160.";
            EmailToSend email = EmailToSend.builder().to(recipient).subject(
                    "Quality Data Warning").body(message).build();
            emailService.sendMail(email);

            logService.addLog("Warning, unit costs exceed $160.",
                    category);
            System.out.println("Warning, unit costs exceed $160.");
        }

        if (next != null)
            next.handle(currentMonthRecord);
    }
}
