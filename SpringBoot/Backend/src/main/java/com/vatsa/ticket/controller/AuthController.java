package com.vatsa.ticket.controller;

import com.vatsa.ticket.model.Users;
import com.vatsa.ticket.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService service;
    private final BCryptPasswordEncoder encoder;

    public AuthController(UserService service, BCryptPasswordEncoder encoder) {
        this.service = service;
        this.encoder = encoder;
    }

    @PostMapping("/register")
    public ResponseEntity<Users> register(@RequestBody Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        Users saved = service.register(user);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users user) {
        String token = service.verify(user);
        return ResponseEntity.ok(token);
    }
}
