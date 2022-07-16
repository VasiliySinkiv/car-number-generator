package com.example.car.number.generator.controller;

import com.example.car.number.generator.service.CarNumberGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/number")
public class CarNumberGeneratorController {


    @Autowired
    private CarNumberGeneratorService numberGeneratorService;

    @GetMapping("/next")
    public HttpEntity<String> getNumberNext() {
        ResponseEntity.BodyBuilder responseEntity = ResponseEntity.status(200);
        String automobileNumber = numberGeneratorService.getNumberNext();
        return responseEntity.body(automobileNumber);
    }

    @GetMapping("/random")
    public HttpEntity<String> getNumberRandom() {
        ResponseEntity.BodyBuilder responseEntity = ResponseEntity.status(200);
        String automobileNumber = numberGeneratorService.getNumberRandom();
        return responseEntity.body(automobileNumber);
    }
}
