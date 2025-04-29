package com.security.authentication.resource;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicAuthResource {

    @GetMapping("/basic/hello")
    public String hello() {
        return "Hello! You are authenticated via HTTP Basic.";
    }
}
