package com.javaproject.order.services;

import com.javaproject.order.dto.OrderDTO;
import com.javaproject.order.exceptions.OrderDoesNotExistException;
import com.javaproject.order.model.Client;
import com.javaproject.order.model.Order;
import com.javaproject.order.repositories.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;
    ClientService clientService;
    OrderDishService dishService;
    ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository, ClientService clientService, OrderDishService dishService, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.clientService = clientService;
        this.dishService = dishService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List <OrderDTO> findByClientAndStatus(Long clientId, String status) {
        Client client = clientService.getClient(clientId);

        if (status != null){
            List <Order> orders = orderRepository.findByClientIdAndStatus(clientId, status);
            return orders.stream().map(order -> modelMapper.map(order, OrderDTO.class)).collect(Collectors.toList());
        } else {
            List <Order> orders = orderRepository.findByClientId(clientId);
            return orders.stream().map(order -> modelMapper.map(order, OrderDTO.class)).collect(Collectors.toList());
        }

    }

    @Override
    public OrderDTO findById(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);

        if(!orderOptional.isPresent()) {
            throw new OrderDoesNotExistException();
        }
        return modelMapper.map(orderOptional.get(), OrderDTO.class);
    }

    @Override
    public OrderDTO save(OrderDTO order) {
        Order savedOrder = orderRepository.save(modelMapper.map(order, Order.class));
        return modelMapper.map(savedOrder, OrderDTO.class);
    }

    @Override
    public boolean delete(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if(!order.isPresent()) {
            throw new OrderDoesNotExistException();
        }
        orderRepository.delete(order.get());
        return true;
    }

    @Override
    public List<OrderDTO> findAll() {
        List<Order> orders = new LinkedList<>();
        orderRepository.findAll().iterator().forEachRemaining(orders::add);
        return orders.stream().map(order -> modelMapper.map(order, OrderDTO.class)).collect(Collectors.toList());
    }


}



