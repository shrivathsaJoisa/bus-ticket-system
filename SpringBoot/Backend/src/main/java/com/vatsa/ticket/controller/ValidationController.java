package com.vatsa.ticket.controller;

import com.vatsa.ticket.dto.ValidationRequest;
import com.vatsa.ticket.dto.ValidationResponse;
import com.vatsa.ticket.service.ValidationService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/validation")
public class ValidationController {

    private final ValidationService validationService;

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidationResponse> validateTicket(
            @RequestBody ValidationRequest request,
            Authentication authentication) {

        String collectorUsername = authentication.getName();
        ValidationResponse response = validationService.validate(request, collectorUsername);

        return ResponseEntity.ok(response);
    }
}
