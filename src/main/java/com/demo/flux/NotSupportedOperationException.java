package com.demo.flux;

public class NotSupportedOperationException extends RuntimeException {

    public NotSupportedOperationException() {
        super("Operation not supported");
    }
}
