package com.example.payments.util;

public class PaymentException extends Exception {
    private static final long serialVersionUID = -3128681006635769411L;

    public PaymentException(String message) {
        super(message);
    }
}
