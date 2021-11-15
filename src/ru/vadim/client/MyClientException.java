package ru.vadim.client;

public class MyClientException extends RuntimeException{
    public MyClientException(String message) {
        super(message);
    }

    public MyClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
