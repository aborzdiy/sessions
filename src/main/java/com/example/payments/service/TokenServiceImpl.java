package com.example.payments.service;

import com.example.payments.model.Token;
import com.example.payments.repository.CrudTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    private final CrudTokenRepository tokenRepository;

    @Autowired
    public TokenServiceImpl(CrudTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token create(Token token) {
        return tokenRepository.save(token);
    }

    @Override
    public Token save(Token token) {
        return tokenRepository.save(token);
    }

    @Override
    public Token findByToken(String token) {
        return tokenRepository.findByToken(token).orElse(null);
    }

    @Override
    public void disableToken(String token) {
        Token curToken = findByToken(token);
        if (curToken != null) {
            curToken.setActive(false);
            tokenRepository.save(curToken);
        }
    }
}
