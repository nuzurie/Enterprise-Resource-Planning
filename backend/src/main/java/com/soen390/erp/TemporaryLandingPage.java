package com.soen390.erp;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = {"http://localhost:3000"})
public class TemporaryLandingPage {

    @GetMapping("")
    public String landingPage(){
        return "Let's ace this!";
    }
}
