package com.tweeny_store.tweeny_store.repository.token;

import com.tweeny_store.tweeny_store.model.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByToken(String token);
}
