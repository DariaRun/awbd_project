package com.javaproject.order.repositories;

import com.javaproject.order.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface OrderRepository extends CrudRepository<Order, Long>{
    List<Order> findByClientId(Long clientId);
    List <Order> findByClientIdAndStatus(Long clientId, String status);
}
