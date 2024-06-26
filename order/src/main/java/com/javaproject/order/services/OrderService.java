package com.javaproject.order.services;

import com.javaproject.order.dto.OrderDTO;
import com.javaproject.order.model.Order;

import java.util.List;

public interface OrderService {
    List <OrderDTO> findByClientAndStatus(Long clientId, String status);
    OrderDTO save(OrderDTO orderDTO);
    List<OrderDTO> findAll();
    boolean delete(Long id);
    OrderDTO findById(Long id);
}
