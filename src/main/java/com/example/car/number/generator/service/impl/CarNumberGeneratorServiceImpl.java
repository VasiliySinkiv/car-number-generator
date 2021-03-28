package com.example.car.number.generator.service.impl;

import com.example.car.number.generator.manager.CarNumberGeneratorManager;
import com.example.car.number.generator.service.CarNumberGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarNumberGeneratorServiceImpl implements CarNumberGeneratorService {

    @Autowired
    private CarNumberGeneratorManager carNumberGeneratorManager;

    @Override
    public String getNumberNext() {
        return carNumberGeneratorManager.generateNextNumber();
    }

    @Override
    public String getNumberRandom() {
        return carNumberGeneratorManager.generateRandomNumber();
    }

}
