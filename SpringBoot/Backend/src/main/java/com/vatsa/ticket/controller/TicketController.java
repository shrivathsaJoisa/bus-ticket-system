package com.vatsa.ticket.controller;

import com.vatsa.ticket.dto.TicketCreateRequest;
import com.vatsa.ticket.dto.TicketDto;
import com.vatsa.ticket.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
// @CrossOrigin(origins = "*")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // POST /tickets  -> book a ticket for logged-in user
    @PostMapping
    public ResponseEntity<TicketDto> bookTicket(@RequestBody TicketCreateRequest request,
                                                Authentication authentication) {
        String username = authentication.getName(); // comes from JWT
        TicketDto dto = ticketService.bookTicket(request, username);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // GET /tickets/my  -> get tickets of logged-in user
    @GetMapping("/my")
    public ResponseEntity<List<TicketDto>> getMyTickets(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(ticketService.getMyTickets(username));
    }
}
