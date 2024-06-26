package com.javaproject.order.services;

import com.javaproject.order.model.Promotion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "promotion")
public interface PromotionServiceProxy {
    @GetMapping("/promotion")
    Promotion findPromotion();
}
