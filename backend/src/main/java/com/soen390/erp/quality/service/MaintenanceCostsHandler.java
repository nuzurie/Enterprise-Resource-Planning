package com.soen390.erp.quality.service;

import com.soen390.erp.email.model.EmailToSend;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaintenanceCostsHandler extends QualityDataHandler{


    @Override
    public void handle(List<String> currentMonthRecord)
    {
        String maintenanceCosts = currentMonthRecord.get(2);

        if (Integer.parseInt(maintenanceCosts) > 5000)
        {
            String message = "Warning, maintenance costs exceed $5000.";
            EmailToSend email = EmailToSend.builder().to(recipient).subject(
                    "Quality Data Warning").body(message).build();
            emailService.sendMail(email);

            logService.addLog("Warning, maintenance costs exceed $5000.",
                    category);

            System.out.println("Warning, maintenance costs exceed $5000.");
        }

        if (next != null)
            next.handle(currentMonthRecord);
    }
}
