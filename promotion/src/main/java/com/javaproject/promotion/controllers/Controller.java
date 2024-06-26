package com.javaproject.promotion.controllers;

import com.javaproject.promotion.config.PropertiesConfig;
import com.javaproject.promotion.model.Promotion;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;

@RestController
public class Controller {
    @Autowired
    private PropertiesConfig configuration;

    private final static Logger logger = LoggerFactory.getLogger(Controller.class);

    @GetMapping("/promotion")
    public Promotion getPromotion() {
        return new Promotion(configuration.getWeek(), configuration.getWeekend(), configuration.getVersionId());
    }
//    @GetMapping("/promotion")
//    public ResponseEntity<Promotion> getPromotion(@RequestHeader("awbd-id") String correlationId) {
//        Promotion promotion = new Promotion(configuration.getWeek(), configuration.getWeekend(), configuration.getVersionId());
//        logger.info("correlation-id promotion: {}", correlationId);
//        return ResponseEntity.status(HttpStatus.OK).body(promotion);
// }
}
