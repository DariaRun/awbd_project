package com.javaproject.order.controller;

import com.javaproject.order.dto.DishDTO;
import com.javaproject.order.dto.OrderDTO;
import com.javaproject.order.dto.OrderDishDTO;
import com.javaproject.order.model.Order;
import com.javaproject.order.model.Promotion;
import com.javaproject.order.services.OrderService;
import com.javaproject.order.services.PromotionServiceProxy;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;



@RestController
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PromotionServiceProxy promotionServiceProxy;

    private final static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Operation(summary = "get all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "orders returned successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema (implementation = Order.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid data",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Orders not found",
                    content = @Content)})
    @GetMapping(value="/order/list")
    public CollectionModel<OrderDTO> findAll(){
        List <OrderDTO> orders = orderService.findAll();
        for (final OrderDTO order : orders) {
            Link selflink = linkTo(methodOn(OrderController.class).getOrder(order.getId())).withSelfRel();
            order.add(selflink);

            Link deleteLink = linkTo(methodOn(OrderController.class).deleteOrder(order.getId())).withRel("deleteOrder");
            order.add(deleteLink);

            Link postLink = linkTo(methodOn(OrderController.class).saveOrder(order)).withRel("saveOrder");
            order.add(postLink);

            Link putLink = linkTo(methodOn(OrderController.class).updateOrder(order)).withRel("updateOrder");
            order.add(putLink);

        }

        Link link = linkTo(methodOn(OrderController.class).findAll()).withSelfRel();
        CollectionModel<OrderDTO> result = CollectionModel.of(orders, link);
        return result;
     }

    @Operation(summary = "get order by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "order returned successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema (implementation = Order.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content)})
    @GetMapping(path="/order/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CircuitBreaker(name="promotionById", fallbackMethod = "getOrderFallback")
    public OrderDTO getOrder(@PathVariable Long orderId){
        OrderDTO order = orderService.findById(orderId);
        Promotion promotion = promotionServiceProxy.findPromotion();
        logger.info(promotion.getVersionId());
        List<OrderDishDTO> dishes = order.getDishes();
        for (final OrderDishDTO dish : dishes) {
            DishDTO dishDTO = dish.getDish();
            if (Objects.equals(dishDTO.getName().toLowerCase(), "pizza") && dish.getQuantity() >= 2)
                dish.setQuantity(dish.getQuantity() + promotion.getWeek());
        }

        return order;
    }

    @Operation(summary = "delete order by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "order deleted",
            content = {@Content(mediaType = "application/json",
            schema = @Schema (implementation = Order.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id",
            content = @Content),
            @ApiResponse(responseCode = "404", description = "Order not found",
            content = @Content)})
    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId){
        boolean deleted = orderService.delete(orderId);

        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "save order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "order saved successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema (implementation = Order.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid data",
                    content = @Content)})
    @PostMapping("/order")
    public ResponseEntity<OrderDTO> saveOrder(@Valid @RequestBody OrderDTO orderDTO){
        OrderDTO savedOrder = orderService.save(orderDTO);
        URI locationURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{order}").buildAndExpand(savedOrder.getId()).toUri();
        Link selfLink = linkTo(methodOn(OrderController.class).getOrder(savedOrder.getId())).withSelfRel();
        savedOrder.add(selfLink);

        return ResponseEntity.created(locationURI).body(savedOrder);
    }

    @Operation(summary = "update order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "order updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema (implementation = Order.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid data",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content)})
    @PutMapping("/order")
    public ResponseEntity<OrderDTO> updateOrder(@Valid @RequestBody OrderDTO orderDTO){
        OrderDTO updatedOrder = orderService.save(orderDTO);

        Link selfLink = linkTo(methodOn(OrderController.class).getOrder(updatedOrder.getId())).withSelfRel();
        updatedOrder.add(selfLink);

        return ResponseEntity.ok(updatedOrder);
    }

    @Operation(summary = "get order by client id and status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "order returned successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema (implementation = Order.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid client id or status",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content)})
    @GetMapping("/order/client/{clientId}/status/{status}")
    @CircuitBreaker(name="promotionById", fallbackMethod = "getOrderByClientAndStatusFallback")
     public List <OrderDTO> findByClientAndStatus (@PathVariable Long clientId,
                                @PathVariable String status) {
        List<OrderDTO> orders = orderService.findByClientAndStatus(clientId, status);
        for(OrderDTO order : orders){
            Link selfLink = linkTo(methodOn(OrderController.class).getOrder(order.getId())).withSelfRel();
            order.add(selfLink);

        }

        Promotion promotion = promotionServiceProxy.findPromotion();
        logger.info(promotion.getVersionId());
        for(final OrderDTO order : orders){
            List<OrderDishDTO> dishes = order.getDishes();
            for (final OrderDishDTO dish : dishes) {
                DishDTO dishDTO = dish.getDish();
                if (Objects.equals(dishDTO.getName().toLowerCase(), "pizza") && dish.getQuantity() > 2 && Objects.equals(order.getStatus(), "processed"))
                    dish.setQuantity(dish.getQuantity() + promotion.getWeek());

            }
        }
        return orders;
     }

     OrderDTO getOrderFallback(Long orderId, Throwable throwable){
        OrderDTO orderDTO = orderService.findById(orderId);
        return orderDTO;
     }

    public List <OrderDTO> getOrderByClientAndStatusFallback(Long clientId, String status, Throwable throwable){
        List<OrderDTO> orders = orderService.findByClientAndStatus(clientId, status);
        return orders;
    }
}
