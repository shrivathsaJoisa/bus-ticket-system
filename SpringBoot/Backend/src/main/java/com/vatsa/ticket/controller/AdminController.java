package com.vatsa.ticket.controller;

import com.vatsa.ticket.dto.TicketDto;
import com.vatsa.ticket.dto.ValidationLogDto;
import com.vatsa.ticket.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
// @CrossOrigin(origins = "*")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // ---------- Tickets ----------

    @GetMapping("/tickets")
    public ResponseEntity<List<TicketDto>> getAllTickets() {
        return ResponseEntity.ok(adminService.getAllTickets());
    }

    @GetMapping("/tickets/by-route/{routeId}")
    public ResponseEntity<List<TicketDto>> getTicketsByRoute(@PathVariable Long routeId) {
        return ResponseEntity.ok(adminService.getTicketsByRoute(routeId));
    }

    // ---------- Validation Logs ----------

    @GetMapping("/validations")
    public ResponseEntity<List<ValidationLogDto>> getAllValidations() {
        return ResponseEntity.ok(adminService.getAllValidationLogs());
    }
}
