package com.nutribite.ai.exception;

public class FoodNotFoundException extends RuntimeException {

    public FoodNotFoundException(Long id) {
        super("Food not found with id: " + id);
    }

    public FoodNotFoundException(String message) {
        super(message);
    }
}