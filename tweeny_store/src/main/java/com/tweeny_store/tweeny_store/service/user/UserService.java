package com.tweeny_store.tweeny_store.service.user;

import com.tweeny_store.tweeny_store.exception.exceptions.DuplicateEmailException;
import com.tweeny_store.tweeny_store.exception.exceptions.UserCreationException;
import com.tweeny_store.tweeny_store.model.role.Role;
import com.tweeny_store.tweeny_store.model.user.User;
import com.tweeny_store.tweeny_store.model.user.UserRequest;
import com.tweeny_store.tweeny_store.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
   User createUser(UserRequest request, Role roles);
}
