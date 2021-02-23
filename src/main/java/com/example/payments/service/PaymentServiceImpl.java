package com.example.payments.service;

import com.example.payments.model.Payment;
import com.example.payments.model.User;
import com.example.payments.repository.CrudPaymentRepository;
import com.example.payments.repository.CrudUserRepository;
import com.example.payments.util.PaymentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final CrudPaymentRepository paymentRepository;
    private final CrudUserRepository userRepository;

    @Autowired
    public PaymentServiceImpl(CrudPaymentRepository crudPaymentRepository, CrudUserRepository userRepository) {
        this.paymentRepository = crudPaymentRepository;
        this.userRepository = userRepository;
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

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = PaymentException.class)
    public void makeOperation(Long amount, User user) throws PaymentException {
        addBalanceAmount(amount, user);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY )
    public void addBalanceAmount(Long amount, User user) throws PaymentException {
        final Long curBalance = userRepository.getCurrentBalance(user.getId());
        final Long newBalance = curBalance + amount;

        if (newBalance < 0) {
            throw new PaymentException(
                    "The money in the account '" + user.getEmail() + "' is not enough (" + curBalance + ")");
        }
        user.setBalance(newBalance);

        paymentRepository.save(new Payment(null, user, amount));

    }
}
