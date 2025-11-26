package com.vatsa.ticket.service;

import com.vatsa.ticket.dto.TicketCreateRequest;
import com.vatsa.ticket.dto.TicketDto;
import com.vatsa.ticket.model.Route;
import com.vatsa.ticket.model.Ticket;
import com.vatsa.ticket.model.Users;
import com.vatsa.ticket.repo.RouteRepo;
import com.vatsa.ticket.repo.TicketRepo;
import com.vatsa.ticket.repo.Userrepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketRepo ticketRepository;
    private final RouteRepo routeRepository;
    private final Userrepo userrepo;

    public TicketService(TicketRepo ticketRepository,
                         RouteRepo routeRepository,
                         Userrepo userrepo) {
        this.ticketRepository = ticketRepository;
        this.routeRepository = routeRepository;
        this.userrepo = userrepo;
    }

    public TicketDto bookTicket(TicketCreateRequest request, String username) {
        // 1. Get logged-in user (passenger)
        Users user = userrepo.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }

        // 2. Get route
        Route route = routeRepository.findById(request.getRouteId())
                .orElseThrow(() -> new RuntimeException("Route not found with id: " + request.getRouteId()));

        // 3. Generate UUID for QR
        String qrToken = UUID.randomUUID().toString();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusDays(30); // or whatever validity you want

        // 4. Create Ticket entity
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setRoute(route);
        ticket.setQrToken(qrToken);
        ticket.setIssuedAt(now);
        ticket.setExpiresAt(expiresAt);
        ticket.setValidated(false);

        Ticket saved = ticketRepository.save(ticket);

        // 5. Return DTO
        return toDto(saved);
    }

    public List<TicketDto> getMyTickets(String username) {
        Users user = userrepo.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }

        return ticketRepository.findByUser(user)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private TicketDto toDto(Ticket ticket) {
        return new TicketDto(
                ticket.getId(),
                ticket.getRoute().getId(),
                ticket.getRoute().getStart(),
                ticket.getRoute().getStop(),
                ticket.getRoute().getFare(),
                ticket.getQrToken(),
                ticket.getIssuedAt(),
                ticket.getExpiresAt(),
                ticket.isValidated()
        );
    }
}
