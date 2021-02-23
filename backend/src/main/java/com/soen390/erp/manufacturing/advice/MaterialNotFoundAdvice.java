package com.soen390.erp.manufacturing.advice;

import com.soen390.erp.manufacturing.exceptions.MaterialNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class MaterialNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(MaterialNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String partNotFoundException(MaterialNotFoundException ex){
        return ex.getMessage();
    }
}
