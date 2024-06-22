package com.tweeny_store.tweeny_store.repository.user;

import com.tweeny_store.tweeny_store.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
