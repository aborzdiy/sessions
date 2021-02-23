package com.example.payments.controller;

public class OperationResult {

    private final boolean ok;
    private final String description;
    private final Object result;

    public OperationResult(boolean ok) {
        this(ok, null);
    }

    public OperationResult(boolean ok, String description) {
        this(ok, description, null);
    }

    public OperationResult(boolean ok, String description, Object result) {
        this.ok = ok;
        this.description = description;
        this.result = result;
    }

    public boolean isOk() {
        return ok;
    }

    public String getDescription() {
        return description;
    }

    public Object getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "OperationResult{" +
                "ok=" + ok +
                ", description='" + description + '\'' +
                '}';
    }
}
