package com.vatsa.ticket.service;

import com.vatsa.ticket.dto.TicketDto;
import com.vatsa.ticket.dto.ValidationLogDto;
import com.vatsa.ticket.model.Ticket;
import com.vatsa.ticket.model.ValidationLog;
import com.vatsa.ticket.repo.TicketRepo;
import com.vatsa.ticket.repo.ValidationLogRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final TicketRepo ticketRepository;
    private final ValidationLogRepo validationLogRepository;

    public AdminService(TicketRepo ticketRepository,
                        ValidationLogRepo validationLogRepository) {
        this.ticketRepository = ticketRepository;
        this.validationLogRepository = validationLogRepository;
    }

    // ---------- Tickets ----------

    public List<TicketDto> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(this::toTicketDto)
                .collect(Collectors.toList());
    }

    public List<TicketDto> getTicketsByRoute(Long routeId) {
        return ticketRepository.findByRoute_Id(routeId)
                .stream()
                .map(this::toTicketDto)
                .collect(Collectors.toList());
    }

    // ---------- Validation Logs ----------

    public List<ValidationLogDto> getAllValidationLogs() {
        return validationLogRepository.findAll()
                .stream()
                .map(this::toValidationDto)
                .collect(Collectors.toList());
    }

    // ---------- Mapping helpers ----------

    private TicketDto toTicketDto(Ticket ticket) {
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

    private ValidationLogDto toValidationDto(ValidationLog log) {
        return new ValidationLogDto(
                log.getId(),
                log.getTicket().getId(),
                log.getTicket().getUser().getUsername(),
                log.getCollector().getUsername(),
                log.getTicket().getRoute().getStart(),
                log.getTicket().getRoute().getStop(),
                log.getTicket().getRoute().getFare(),
                log.getStatus().name(),
                log.getValidatedAt()
        );
    }
}
