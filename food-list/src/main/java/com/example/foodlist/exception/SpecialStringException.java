package com.example.foodlist.exception;

public class SpecialStringException extends RuntimeException{
    SpecialStringException() {}

    SpecialStringException(String message) {
        super(message);
    }
}
