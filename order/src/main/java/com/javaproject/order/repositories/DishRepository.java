package com.javaproject.order.repositories;

import com.javaproject.order.model.Dish;
import org.springframework.data.repository.CrudRepository;

public interface DishRepository extends CrudRepository<Dish, Long> {
    Dish findByName(String name);
}
