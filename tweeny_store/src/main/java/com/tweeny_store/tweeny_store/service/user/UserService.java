package com.tweeny_store.tweeny_store.service.user;

import com.tweeny_store.tweeny_store.exception.exceptions.DuplicateEmailException;
import com.tweeny_store.tweeny_store.exception.exceptions.UserCreationException;
import com.tweeny_store.tweeny_store.model.user.User;
import com.tweeny_store.tweeny_store.model.user.UserRequest;
import com.tweeny_store.tweeny_store.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import static com.tweeny_store.tweeny_store.exception.BussinessErrorCode.DUPLICATE_EMAIL;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(UserRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(request.getPassword())
                .address(request.getAddress())
                .phone(request.getPhone())
                .accountLocked(false)
                .enabled(false)
                .build();
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEmailException("Email already exists.");
        } catch (Exception e) {
            throw new UserCreationException("Error create user");
        }
    }
}
