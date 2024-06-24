package com.tweeny_store.tweeny_store.impl.user;

import com.tweeny_store.tweeny_store.exception.exceptions.DuplicateEmailException;
import com.tweeny_store.tweeny_store.exception.exceptions.UserCreationException;
import com.tweeny_store.tweeny_store.model.role.Role;
import com.tweeny_store.tweeny_store.model.user.User;
import com.tweeny_store.tweeny_store.model.user.UserRequest;
import com.tweeny_store.tweeny_store.repository.user.UserRepository;
import com.tweeny_store.tweeny_store.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.tweeny_store.tweeny_store.exception.BussinessErrorCode.DUPLICATE_EMAIL;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(UserRequest request, Role roles) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(List.of(roles))
                .accountLocked(false)
                .enabled(false)
                .build();
        try {
            userRepository.save(user);
            return user;
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEmailException("Email already exists.");
        } catch (Exception e) {
            throw new UserCreationException("Error create user");
        }
    }
}
