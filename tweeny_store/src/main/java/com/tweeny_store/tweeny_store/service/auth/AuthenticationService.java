package com.tweeny_store.tweeny_store.service.auth;

import com.tweeny_store.tweeny_store.model.auth.AuthenticationRequest;
import com.tweeny_store.tweeny_store.model.auth.AuthenticationResponse;
import com.tweeny_store.tweeny_store.model.role.Role;
import com.tweeny_store.tweeny_store.model.user.User;
import com.tweeny_store.tweeny_store.model.user.UserRequest;
import com.tweeny_store.tweeny_store.repository.role.RoleRepository;
import com.tweeny_store.tweeny_store.service.user.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final RoleRepository roleRepository;
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;
    public void register(UserRequest request) throws MessagingException {
        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalArgumentException("Role USER was not initialized"));
        var user = userService.createUser(request, userRole);
    }
}
