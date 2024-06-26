package com.javaproject.order.exceptions;

public class OrderDoesNotExistException extends RuntimeException {
    public OrderDoesNotExistException() { super("The order does not exist."); }
}
