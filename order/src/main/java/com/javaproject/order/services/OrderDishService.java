package com.javaproject.order.services;

import com.javaproject.order.dto.OrderDishDTO;
import com.javaproject.order.model.OrderDish;

import java.util.List;

public interface OrderDishService {
    List<OrderDishDTO> findAll();
    OrderDishDTO findByOrder(Long orderId);
    OrderDishDTO save(OrderDishDTO orderDish);
    boolean delete(Long orderDishId);
    OrderDishDTO findById(Long id);
}
