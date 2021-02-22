package com.example.payments;

import com.example.payments.model.User;

import java.util.Collections;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), Collections.emptySet());
    }
}
