package com.example.restapi.controllers;

import com.example.restapi.models.responses.PingPongResponse;
import com.example.restapi.models.responses.PingResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "Welcome to Rest API from Long Nguyen!";
    }

    @GetMapping("/ping")
    public PingResponse ping() {
        return PingResponse.builder()
                .errorCode(HttpStatus.OK)
                .message("Success")
                .build();
    }


    @GetMapping("/ping-pong")
    public PingPongResponse pingPong() {
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.OK);
        return PingPongResponse.builder()
                .errorCode(response.getStatusCode().value())
                .message("Success")
                .build();
    }
}
