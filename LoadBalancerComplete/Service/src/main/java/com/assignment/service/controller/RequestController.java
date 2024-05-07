package com.assignment.service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "service")
public class RequestController {
    @Value("${spring.application.name}")
    String application;

    @Value("${server.port}")
    String currentPort;
    @GetMapping()
    public ResponseEntity<String> getAcknowledge() {
        String acknowledgement = "Acknowledged by "+application+" at port "+currentPort;
        return new ResponseEntity<>(acknowledgement, HttpStatus.OK);
    }
    @GetMapping("paramRequest")
    public ResponseEntity<String> getParamResponse(@RequestParam String param) {
        String result = "This parameter come to request: "+param;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("{num}")
    public ResponseEntity<String> getPathResponse(@PathVariable int num) {
        String result = "This parameter come to request: "+num;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("postRequest")
    public ResponseEntity<String> getPathResponse(@RequestBody String param) {
        String result = "This request body come to the "+application+": "+param;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
