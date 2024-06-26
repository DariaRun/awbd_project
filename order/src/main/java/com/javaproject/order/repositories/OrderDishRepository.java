package com.javaproject.order.repositories;

import com.javaproject.order.model.OrderDish;
import org.springframework.data.repository.CrudRepository;

public interface OrderDishRepository extends CrudRepository<OrderDish, Long> {
    OrderDish findByOrderId(Long orderId);
}
