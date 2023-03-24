package com.dobe.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public  String hello() {
        return " <h1>Hello World! le serveur marche</h1>";

    }
}
