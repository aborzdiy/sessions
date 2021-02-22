package com.example.payments.service;

import com.example.payments.model.Payment;
import com.example.payments.model.User;
import com.example.payments.repository.CrudPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final CrudPaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(CrudPaymentRepository crudPaymentRepository) {
        this.paymentRepository = crudPaymentRepository;
    }

    @Override
    public Payment create(Payment payment, User user) {
        payment.setUser(user);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment get(int id, User user) {
        return paymentRepository.getByIdAndUser(id, user).orElse(null);
    }

    @Override
    public List<Payment> getAll(User user) {
        return paymentRepository.getByUser(user);
    }
}
