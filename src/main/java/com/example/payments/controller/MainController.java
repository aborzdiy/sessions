package com.example.payments.controller;

import com.example.payments.components.JwtProvider;
import com.example.payments.model.User;
import com.example.payments.service.PaymentService;
import com.example.payments.service.UserService;
import com.example.payments.util.PaymentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MainController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final PaymentService paymentService;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Autowired
    public MainController(PaymentService paymentService, UserService userService, JwtProvider jwtProvider) {
        this.paymentService = paymentService;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public OperationResult login(@RequestBody AuthRequest request) {
        User user;
        try {
            user = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        } catch (UsernameNotFoundException exception) {
            return new OperationResult(false, "unknown credentials");
        }
        if (user == null) {
            return new OperationResult(false, "unknown credentials");
        }
        String token = jwtProvider.generateToken(user.getEmail(), request.getDeviceId());
        return new OperationResult(true, null, token);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        log.info("logout");
        jwtProvider.disableToken(request);
    }

    @PostMapping(path = "/payment", produces = MediaType.APPLICATION_JSON_VALUE)
    public OperationResult makePayment(@AuthenticationPrincipal User user) {
        log.info("make payment");
        if (user == null) {
            return new OperationResult(false, "unknown credentials");
        }
        try {
            paymentService.makeOperation(-110L, user);
            return new OperationResult(true);
        }catch (PaymentException exception) {
            return new OperationResult(false, exception.getMessage());
        }
    }
}
