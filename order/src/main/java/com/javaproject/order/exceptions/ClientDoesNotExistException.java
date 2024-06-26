package com.javaproject.order.exceptions;

public class ClientDoesNotExistException extends RuntimeException{
    public ClientDoesNotExistException() {
        super("The client does not exist.");
    }
}
