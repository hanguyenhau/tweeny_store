package com.tweeny_store.tweeny_store.impl.auth;

import com.tweeny_store.tweeny_store.model.auth.AuthenticationRequest;
import com.tweeny_store.tweeny_store.model.auth.AuthenticationResponse;
import com.tweeny_store.tweeny_store.model.token.Token;
import com.tweeny_store.tweeny_store.model.user.User;
import com.tweeny_store.tweeny_store.model.user.UserRequest;
import com.tweeny_store.tweeny_store.repository.role.RoleRepository;
import com.tweeny_store.tweeny_store.repository.token.TokenRepository;
import com.tweeny_store.tweeny_store.repository.user.UserRepository;
import com.tweeny_store.tweeny_store.security.JwtService;
import com.tweeny_store.tweeny_store.service.auth.AuthenticationService;
import com.tweeny_store.tweeny_store.service.email.EmailService;
import com.tweeny_store.tweeny_store.service.user.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public void register(UserRequest request) throws Exception {
        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalArgumentException("Role USER was not initialized"));
        var user = userService.createUser(request, userRole);
        sendValidationEmail(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String, Object>();
        var user  = (User) auth.getPrincipal();
        claims.put("fullname", user.fullName());
        var jwtToken = jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void activateAccount(String token) throws Exception {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(()-> new RuntimeException("Invalid Token"));
        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been to the email " + savedToken.getUser().getEmail());
        }
        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"))   ;
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidateAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

    @Override
    public void resendToken(String email) throws Exception {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Email is not register yet"));
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws Exception {
        var newToken = generateAndSaveActivationToken(user);
        emailService.sendSimpleMailMessage(
                user.fullName(),
                user.getEmail(),
                newToken);
    }

    private String generateAndSaveActivationToken(User user) {
        String generateToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generateToken)
                .createAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generateToken;
    }

    private String generateActivationCode(int length) {
        String character = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for(int i = 0; i<length; i++){
            int randomIndex = secureRandom.nextInt(character.length());
            codeBuilder.append(character.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }
}
