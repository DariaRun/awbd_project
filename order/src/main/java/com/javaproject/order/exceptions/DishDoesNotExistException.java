package com.javaproject.order.exceptions;

public class DishDoesNotExistException extends RuntimeException {
    public DishDoesNotExistException() { super("The dish does not exist."); }
}
