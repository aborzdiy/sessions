package com.example.payments.repository;

import com.example.payments.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CrudTokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByToken(String token);
}
