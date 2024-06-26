package com.javaproject.order.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class ClientDTO extends RepresentationModel<ClientDTO> {
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Address cannot be blank")
    private String address;

    public ClientDTO() {
    }

    public ClientDTO(String name, String email, String address, Long id) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.id = id;
    }
}
