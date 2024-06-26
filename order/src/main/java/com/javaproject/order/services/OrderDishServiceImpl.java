package com.javaproject.order.services;

import com.javaproject.order.dto.OrderDishDTO;
import com.javaproject.order.exceptions.OrderDoesNotExistException;
import com.javaproject.order.model.OrderDish;
import com.javaproject.order.repositories.OrderDishRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderDishServiceImpl implements OrderDishService {

    OrderDishRepository orderDishRepository;
    ModelMapper modelMapper;

    public OrderDishServiceImpl(OrderDishRepository orderDishRepository, ModelMapper modelMapper) {
        this.orderDishRepository = orderDishRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderDishDTO findByOrder(Long orderId) {
        OrderDish orderDish = orderDishRepository.findByOrderId(orderId);
        return modelMapper.map(orderDish, OrderDishDTO.class);
    }

    @Override
    public OrderDishDTO save (OrderDishDTO orderDish) {
        OrderDish orderDishSaved = orderDishRepository.save(modelMapper.map(orderDish, OrderDish.class));
        return modelMapper.map(orderDishSaved, OrderDishDTO.class);
    }

    @Override
    public List<OrderDishDTO> findAll() {
        List<OrderDish> orderDishes = new LinkedList<>();
        orderDishRepository.findAll().iterator().forEachRemaining(orderDishes::add);
        return orderDishes.stream().map(orderDish -> modelMapper.map(orderDish, OrderDishDTO.class)).collect(Collectors.toList());
    }

    @Override
    public boolean delete (Long id) {
        Optional<OrderDish> orderDishOptional = orderDishRepository.findById(id);
        if (!orderDishOptional.isPresent())
            throw new OrderDoesNotExistException();
        orderDishRepository.delete(orderDishOptional.get());
        return true;

    }

    @Override
    public OrderDishDTO findById(Long id) {
        Optional<OrderDish> orderDishOptional = orderDishRepository.findById(id);
        if (!orderDishOptional.isPresent())
            throw new OrderDoesNotExistException();
        return modelMapper.map(orderDishOptional.get(), OrderDishDTO.class);
    }
}
