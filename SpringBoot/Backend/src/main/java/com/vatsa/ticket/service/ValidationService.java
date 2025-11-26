package com.vatsa.ticket.service;

import com.vatsa.ticket.dto.ValidationRequest;
import com.vatsa.ticket.dto.ValidationResponse;
import com.vatsa.ticket.model.*;
import com.vatsa.ticket.repo.TicketRepo;
import com.vatsa.ticket.repo.Userrepo;
import com.vatsa.ticket.repo.ValidationLogRepo;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ValidationService {

    private final TicketRepo ticketRepository;
    private final Userrepo userrepo;
    private final ValidationLogRepo validationLogRepository;

    public ValidationService(TicketRepo ticketRepository,
                             Userrepo userrepo,
                             ValidationLogRepo validationLogRepository) {
        this.ticketRepository = ticketRepository;
        this.userrepo = userrepo;
        this.validationLogRepository = validationLogRepository;
    }

    public ValidationResponse validate(ValidationRequest request, String collectorUsername) {

        Users collector = userrepo.findByUsername(collectorUsername);
        if (collector == null) {
            throw new RuntimeException("Collector not found");
        }

        Ticket ticket = ticketRepository.findByQrToken(request.getQrToken())
                .orElse(null);

        if (ticket == null) {
            return invalidResponse("INVALID", "Ticket not found", null);
        }

        // Check if already validated
        if (ticket.isValidated()) {
            return invalidResponse("ALREADY_VALIDATED", "Ticket already validated", ticket);
        }

        // Check expiry
        if (ticket.getExpiresAt().isBefore(LocalDateTime.now())) {
            return invalidResponse("EXPIRED", "Ticket has expired", ticket);
        }

        // Mark as validated
        ticket.setValidated(true);
        ticketRepository.save(ticket);

        // Save validation log
        ValidationLog log = new ValidationLog(
                ticket,
                collector,
                ValidationStatus.SUCCESS,
                LocalDateTime.now()
        );
        validationLogRepository.save(log);

        return new ValidationResponse(
                "VALID",
                "Ticket is valid",
                ticket.getId(),
                ticket.getUser().getUsername(),
                ticket.getRoute().getStart(),
                ticket.getRoute().getStop(),
                ticket.getRoute().getFare(),
                LocalDateTime.now()
        );
    }

    private ValidationResponse invalidResponse(String status, String message, Ticket ticket) {
        if (ticket == null) {
            return new ValidationResponse(status, message, null, null, null, null, null, null);
        }

        return new ValidationResponse(
                status,
                message,
                ticket.getId(),
                ticket.getUser().getUsername(),
                ticket.getRoute().getStart(),
                ticket.getRoute().getStop(),
                ticket.getRoute().getFare(),
                null
        );
    }
}
