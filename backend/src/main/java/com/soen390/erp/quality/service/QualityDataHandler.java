package com.soen390.erp.quality.service;

import com.soen390.erp.configuration.service.LogService;
import com.soen390.erp.email.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QualityDataHandler implements Handler {
    Handler next;
    private List<Handler> handlers;
    String recipient;
    String category;
    EmailService emailService;
    LogService logService;

    public QualityDataHandler(){}

    public QualityDataHandler(EmailService emailService,
                              LogService logService)
    {
        this.category = "quality data";
        this.recipient = "manager@gmail.com";
        this.logService = logService;
        this.emailService = emailService;
        this.handlers = new ArrayList();
    }

    public void add(Handler handler)
    {
        handlers.add(handler);
    }

    @Override
    public void setNext(Handler handler)
    {
        this.next = handler;
    }

    @Override
    public void handle(List<String> currentMonthRecord)
    {
        handlers.get(0).handle(currentMonthRecord);
    }
    public EmailService getEmailService() { return emailService; }
}
