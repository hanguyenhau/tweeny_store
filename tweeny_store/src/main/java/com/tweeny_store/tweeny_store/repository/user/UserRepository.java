package com.tweeny_store.tweeny_store.repository.user;

import com.tweeny_store.tweeny_store.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
