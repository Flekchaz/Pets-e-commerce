package com.example.securityservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class TestRestApi {

    @GetMapping("/dataTest")
    public Map<String, Object> dataTests(){
        return Map.of("message","data");
    }

}

