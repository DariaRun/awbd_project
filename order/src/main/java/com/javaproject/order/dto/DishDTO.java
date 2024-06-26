package com.javaproject.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class DishDTO extends RepresentationModel<DishDTO> {
    private Long id;
    @NotBlank
    private String name;
    @Positive(message = "Price must be positive")
    private float price;


    public DishDTO() {
    }

    public DishDTO(Long id, String name, float price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
