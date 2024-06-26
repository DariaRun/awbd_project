package com.javaproject.order.services;

import com.javaproject.order.dto.DishDTO;
import com.javaproject.order.model.Dish;
import java.util.List;

public interface DishService {
     Dish getDish(Long dishId);
     List<Dish> getDishes(List<Long> dishIds);
     Dish updateDish(Dish dish);
     boolean deleteDish(Long id);
     DishDTO save (DishDTO dishDTO);
}