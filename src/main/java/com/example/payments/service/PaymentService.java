package com.example.payments.service;

import com.example.payments.model.Payment;
import com.example.payments.model.User;
import com.example.payments.util.PaymentException;

import java.util.List;

public interface PaymentService {

    Payment create(Payment payment, User user);

    Payment get(int id, User user);

    List<Payment> getAll(User user);

    void makeOperation(Long amount, User user) throws PaymentException;

    void addBalanceAmount(Long amount, User user) throws PaymentException;

}
