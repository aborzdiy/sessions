package com.example.payments.service;

import com.example.payments.model.Payment;
import com.example.payments.model.User;

import java.util.List;

public interface PaymentService {

    Payment create(Payment payment, User user);

    Payment get(int id, User user);

    List<Payment> getAll(User user);

}
