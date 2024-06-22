package com.tweeny_store.tweeny_store.service.user;

import com.tweeny_store.tweeny_store.model.user.User;
import com.tweeny_store.tweeny_store.model.user.UserRequest;
import com.tweeny_store.tweeny_store.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        userRepository.save(user);
    }
}
