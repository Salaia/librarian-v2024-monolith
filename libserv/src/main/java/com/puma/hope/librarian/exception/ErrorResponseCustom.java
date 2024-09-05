package com.puma.hope.librarian.exception;

public class ErrorResponseCustom {
    private final String error;

    public ErrorResponseCustom(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
