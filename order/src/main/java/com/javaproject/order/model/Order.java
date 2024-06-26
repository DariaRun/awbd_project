package com.javaproject.order.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order extends RepresentationModel<Order> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDish> orderDishes;


    @Value("#{T(java.util.Arrays).asList('processed', 'delivered', 'cancelled')}")
    private String status;

    public Order() {
    }

    public Order(Client client, String status) {
        this.client = client;
        this.status = status;
    }

    public Order(Client client, List<OrderDish> orderDishes, String status) {
        this.client = client;
        this.orderDishes = orderDishes;
        this.status = status;
    }
}
