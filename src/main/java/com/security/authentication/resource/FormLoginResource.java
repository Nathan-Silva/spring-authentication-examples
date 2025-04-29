package com.security.authentication.resource;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FormLoginResource {

    @GetMapping("/form/hello")
    public String hello() {
        return "Hello, authenticated user with Form Login!";
    }
}
