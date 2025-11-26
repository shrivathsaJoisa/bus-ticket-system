package com.vatsa.ticket.controller;

import com.vatsa.ticket.dto.MeDto;
import com.vatsa.ticket.model.Users;
import com.vatsa.ticket.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;
    private final BCryptPasswordEncoder encoder;

    public UserController(UserService service, BCryptPasswordEncoder encoder) {
        this.service = service;
        this.encoder = encoder;
    }

    // 🔹 Admin: get all users
    @GetMapping("/all")
    public List<Users> getAllUsers() {
        return service.getAllUsers();
    }

    // 🔹 Any logged-in user: get own profile
    @GetMapping("/me")
    public ResponseEntity<MeDto> getMe(Authentication authentication) {
        String username = authentication.getName();
        MeDto me = service.getCurrentUser(username);
        return ResponseEntity.ok(me);
    }
}
