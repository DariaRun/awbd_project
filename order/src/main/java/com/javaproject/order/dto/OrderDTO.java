package com.javaproject.order.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
public class OrderDTO extends RepresentationModel<OrderDTO> {
    private Long id;
    private ClientDTO client;
    private List<OrderDishDTO> dishes;
    private String status;

    public OrderDTO() {}
    public OrderDTO(Long id, ClientDTO client, List<OrderDishDTO> dishes, String status) {
        this.id = id;
        this.client = client;
        this.dishes = dishes;
        this.status = status;
    }
}
