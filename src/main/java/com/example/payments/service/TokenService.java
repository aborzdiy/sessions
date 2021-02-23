package com.example.payments.service;

import com.example.payments.model.Token;

public interface TokenService {

    Token create(Token token);

    Token save(Token token);

    Token findByToken(String token);

    void disableToken(String token);

}
