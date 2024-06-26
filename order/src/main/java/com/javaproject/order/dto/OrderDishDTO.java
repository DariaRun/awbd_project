package com.javaproject.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class OrderDishDTO extends RepresentationModel<OrderDishDTO> {
    private Long id;
    private DishDTO dish;
    private int quantity;

    public OrderDishDTO(){}

    public OrderDishDTO(Long id, DishDTO dish, int quantity) {
        this.id = id;
        this.dish = dish;
        this.quantity = quantity;
    }
}
