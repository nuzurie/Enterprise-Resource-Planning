package com.soen390.erp.quality.service;

import com.soen390.erp.configuration.service.LogService;
import com.soen390.erp.email.model.EmailToSend;
import com.soen390.erp.email.service.EmailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefectDensityHandler extends QualityDataHandler{

    @Override
    public void handle(List<String> currentMonthRecord)
    {
        String defectDensity = currentMonthRecord.get(5);

        if (Integer.parseInt(defectDensity) > 1)
        {
            String message = "Warning, defect density exceeds 15%.";
            EmailToSend email = EmailToSend.builder().to(recipient).subject(
                    "Quality Data Warning").body(message).build();
            getEmailService().sendMail(email);

            logService.addLog("Warning, defect density exceeds 15%.",
                    category);
            System.out.println("Warning, defect density exceeds 15%.");
        }

        if (next != null)
            next.handle(currentMonthRecord);
    }
}
