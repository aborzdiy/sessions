package com.example.payments.components;

import com.example.payments.model.Payment;
import com.example.payments.model.User;
import com.example.payments.service.PaymentService;
import com.example.payments.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final UserService userService;
    private final PaymentService paymentService;

    @Autowired
    public ApplicationStartup(UserService userService, PaymentService paymentService) {
        this.userService = userService;
        this.paymentService = paymentService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        final User user = userService.create(new User(null, "user@mail.ru", "user"));
        final Payment payment1 = paymentService.create(new Payment(null, user, 800L), user);
        final User admin = userService.create(new User(null, "admin@gmail.com", "admin"));
        final Payment payment2 = paymentService.create(new Payment(null, admin, 1000L), admin);
    }
}
