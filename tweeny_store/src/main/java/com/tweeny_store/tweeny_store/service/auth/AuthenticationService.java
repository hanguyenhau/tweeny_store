package com.tweeny_store.tweeny_store.service.auth;

import com.tweeny_store.tweeny_store.model.auth.AuthenticationRequest;
import com.tweeny_store.tweeny_store.model.auth.AuthenticationResponse;
import com.tweeny_store.tweeny_store.model.user.UserRequest;
import jakarta.mail.MessagingException;

public interface AuthenticationService {
    void register(UserRequest request) throws MessagingException;
    AuthenticationResponse authenticate(AuthenticationRequest request);
    void activateAccount(String token);
}

