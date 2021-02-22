package com.example.payments.repository;

import com.example.payments.model.Payment;
import com.example.payments.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudPaymentRepository extends JpaRepository<Payment, Integer> {

    Optional<Payment> getByIdAndUser(int id, User user);

    List<Payment> getByUser(User user);
}
