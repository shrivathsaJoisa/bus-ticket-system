package com.vatsa.ticket.service;

import com.vatsa.ticket.dto.MeDto;
import com.vatsa.ticket.model.Users;
import com.vatsa.ticket.repo.Userrepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final Userrepo repo;
    private final AuthenticationManager authManager;
    private final JWTService jwtService;

    public UserService(Userrepo repo,
                       AuthenticationManager authManager,
                       JWTService jwtService) {
        this.repo = repo;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    public Users register(Users user) {
        return repo.save(user);
    }

    public String verify(Users user) {
        Authentication authentication =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getUsername(),
                                user.getPassword()
                        )
                );

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        }
        return "Failure";
    }

    public List<Users> getAllUsers() {
        return repo.findAll();
    }

    // 🔹 used for /users/me endpoint
    public MeDto getCurrentUser(String username) {
        Users user = repo.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }

        return new MeDto(
                user.getId(),
                user.getUsername(),
                user.getUsername(),      // change if your field is different
                user.getPhone(),     // change if field name differs
                user.getRole().name()
        );
    }
}
