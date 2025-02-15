package com.bitirmeproje.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/getir")
    public String auth() {
        return "Hello World";
    }

}
