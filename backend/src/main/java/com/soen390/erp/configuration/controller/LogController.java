package com.soen390.erp.configuration.controller;

import com.soen390.erp.configuration.model.Log;
import com.soen390.erp.configuration.repository.LogRepository;
import com.soen390.erp.configuration.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LogController {

    public LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping(value = "logs")
    public ResponseEntity<?> all(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize){

        List<Log> allLogs = logService.getAllLogs(pageNo, pageSize);

        return ResponseEntity.ok().body(allLogs);

    }
}
