package br.com.orderingsystem.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("Could not find user: " + id + "\n");
    }

}