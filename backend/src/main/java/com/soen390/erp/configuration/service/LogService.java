package com.soen390.erp.configuration.service;

import com.soen390.erp.configuration.model.Log;
import com.soen390.erp.configuration.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    public final LogRepository logRepository;

    @Autowired
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void addLog(String message){
        Log log = new Log(message);
        logRepository.save(log);
    }
}
