package com.amaap.books.service.exception;

public class BookAlreadyExistException extends Exception {
    public BookAlreadyExistException(String message) {
        super(message);
    }
}
