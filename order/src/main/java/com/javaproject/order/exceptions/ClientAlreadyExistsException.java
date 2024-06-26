package com.javaproject.order.exceptions;

public class ClientAlreadyExistsException extends RuntimeException{
    public ClientAlreadyExistsException() {
        super("A client with the same email already exists.");
    }
}