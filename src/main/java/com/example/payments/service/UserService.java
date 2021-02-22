package com.example.payments.service;

import com.example.payments.model.User;

public interface UserService {
    User getByEmail(String email);

    User create(User user);
}
