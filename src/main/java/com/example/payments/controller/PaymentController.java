package com.example.payments.controller;

import com.example.payments.AuthorizedUser;
import com.example.payments.model.Payment;
import com.example.payments.model.User;
import com.example.payments.service.PaymentService;
import com.example.payments.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = PaymentController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {

    public static final String REST_URL = "/payments";

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final PaymentService paymentService;
    private final UserService userService;

    @Autowired
    public PaymentController(PaymentService paymentService, UserService userService) {
        this.paymentService = paymentService;
        this.userService = userService;
    }

    @GetMapping("/")
    public List<Payment> getPayments(Principal principal) {
        log.info("get payments");
        return paymentService.getAll(userService.getByEmail(principal.getName()));
    }

    @GetMapping("/{id}")
    public Payment getPayment(@PathVariable int id, Principal principal) {
        log.info("get id={}", id);
        return paymentService.get(id, userService.getByEmail(principal.getName()));
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Payment> createWithLocation(@RequestBody Payment payment,
                                                      Principal principal) {

        User userModel = userService.getByEmail(principal.getName());
        log.info("create {}, user {}", payment, userModel);

        Payment created = paymentService.create(payment, userModel);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(payment.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

}
