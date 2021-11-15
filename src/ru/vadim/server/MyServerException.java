package ru.vadim.server;

public class MyServerException extends RuntimeException{
    public MyServerException(String message) {
        super(message);
    }

    public MyServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
