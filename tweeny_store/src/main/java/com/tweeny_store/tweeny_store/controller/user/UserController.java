package com.tweeny_store.tweeny_store.controller.user;

import com.tweeny_store.tweeny_store.model.user.UserRequest;
import com.tweeny_store.tweeny_store.service.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {
    private final UserService service;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRequest request) throws Exception{
        service.createUser(request);
        return ResponseEntity.accepted().build();
    }
}
