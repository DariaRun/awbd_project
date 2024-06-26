package com.javaproject.order.services;

import com.javaproject.order.dto.DishDTO;
import com.javaproject.order.exceptions.DishDoesNotExistException;
import com.javaproject.order.model.Dish;
import com.javaproject.order.repositories.DishRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Validated
public class DishServiceImpl implements DishService {

    DishRepository dishRepository;
    ModelMapper modelMapper;

    public DishServiceImpl(DishRepository dishRepository, ModelMapper modelMapper) {
        this.dishRepository = dishRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Dish getDish(Long dishId) {
        Optional<Dish> dish = dishRepository.findById(dishId);

        if(dish.isEmpty()) {
            throw new DishDoesNotExistException();
        }

        return dish.get();
    }

    @Override
    public List<Dish> getDishes(List<Long> dishIds) {
        List<Dish> dishes = new ArrayList<>();
        for (int i = 0; i < dishIds.size(); i++) {
            Optional<Dish> dish = dishRepository.findById(dishIds.get(i));

            if(dish.isPresent()) {
                dishes.add(dish.get());
            }
            else {
                throw new DishDoesNotExistException();
            }
        }

        return dishes;
    }

    @Override
    public Dish updateDish(Dish dish) {
        Optional<Dish> existingDish = dishRepository.findById(dish.getId());

        if(existingDish.isEmpty()) {
            throw new DishDoesNotExistException();
        }

        return dishRepository.save(dish);
    }

    @Override
    public boolean deleteDish(Long id) {
        Optional<Dish> dishOptional = dishRepository.findById(id);
        if (dishOptional.isPresent()) {
            Dish dish = dishOptional.get();
            dishRepository.delete(dish);
            return true;
        } else {
            throw new DishDoesNotExistException();
        }
    }

    @Override
    public DishDTO save (DishDTO dishDTO) {
        Dish savedDish = dishRepository.save(modelMapper.map(dishDTO, Dish.class));
        return  modelMapper.map(savedDish, DishDTO.class);
    }

}