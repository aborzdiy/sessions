package com.example.payments.controller;

import com.example.payments.service.PaymentService;
import com.example.payments.service.UserService;
import com.example.payments.util.PaymentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MainController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final PaymentService paymentService;
    private final UserService userService;

    @Autowired
    public MainController(PaymentService paymentService, UserService userService) {
        this.paymentService = paymentService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public void login() {

    }

    @PostMapping("/logout")
    public void logout() {

    }

    @PostMapping("/payment")
    public OperationResult makePayment(Principal principal) {
        log.info("make payment");
        try {
            paymentService.makeOperation(110L, userService.getByEmail(principal.getName()));
            return new OperationResult(true);
        }catch (PaymentException exception) {
            return new OperationResult(false, exception.getMessage());
        }
    }
}
