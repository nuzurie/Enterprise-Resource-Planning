package com.soen390.erp.manufacturing.advice;

import com.soen390.erp.manufacturing.exceptions.PartNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class PartNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(PartNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String partNotFoundException(PartNotFoundException ex){
        return ex.getMessage();
    }
}
