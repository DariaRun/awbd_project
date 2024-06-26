package com.javaproject.order.services;

import com.javaproject.order.model.Promotion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "promotion")
public interface PromotionServiceProxy {
    @GetMapping("/promotion")
    Promotion findPromotion();
}

//@FeignClient(value = "promotion")
//public interface PromotionServiceProxy {
//    @GetMapping("/promotion")
//    ResponseEntity<Promotion> findPromotion(@RequestHeader("awbd-id") String correlationId);
//}
