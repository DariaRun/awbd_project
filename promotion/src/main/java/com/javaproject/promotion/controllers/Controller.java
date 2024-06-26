package com.javaproject.promotion.controllers;

import com.javaproject.promotion.config.PropertiesConfig;
import com.javaproject.promotion.model.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    private PropertiesConfig configuration;

    @GetMapping("/promotion")
    public Promotion getPromotion() {
        return new Promotion(configuration.getWeek(), configuration.getWeekend(), configuration.getVersionId());
    }
}
