package com.example.payments.controller;

public class OperationResult {

    private final boolean ok;
    private final String description;

    public OperationResult(boolean ok) {
        this(ok, null);
    }

    public OperationResult(boolean ok, String description) {
        this.ok = ok;
        this.description = description;
    }

    @Override
    public String toString() {
        return "OperationResult{" +
                "ok=" + ok +
                ", description='" + description + '\'' +
                '}';
    }
}
