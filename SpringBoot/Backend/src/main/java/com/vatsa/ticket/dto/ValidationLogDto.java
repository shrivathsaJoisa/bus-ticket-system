package com.vatsa.ticket.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ValidationLogDto {

    private Long id;
    private Long ticketId;
    private String passengerName;
    private String collectorName;
    private String start;
    private String stop;
    private BigDecimal fare;
    private String status;
    private LocalDateTime validatedAt;

    public ValidationLogDto() {
    }

    public ValidationLogDto(Long id, Long ticketId, String passengerName,
                            String collectorName, String start, String stop,
                            BigDecimal fare, String status, LocalDateTime validatedAt) {
        this.id = id;
        this.ticketId = ticketId;
        this.passengerName = passengerName;
        this.collectorName = collectorName;
        this.start = start;
        this.stop = stop;
        this.fare = fare;
        this.status = status;
        this.validatedAt = validatedAt;
    }

    // getters & setters...

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getTicketId() { return ticketId; }
    public void setTicketId(Long ticketId) { this.ticketId = ticketId; }

    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }

    public String getCollectorName() { return collectorName; }
    public void setCollectorName(String collectorName) { this.collectorName = collectorName; }

    public String getStart() { return start; }
    public void setStart(String start) { this.start = start; }

    public String getStop() { return stop; }
    public void setStop(String stop) { this.stop = stop; }

    public BigDecimal getFare() { return fare; }
    public void setFare(BigDecimal fare) { this.fare = fare; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getValidatedAt() { return validatedAt; }
    public void setValidatedAt(LocalDateTime validatedAt) { this.validatedAt = validatedAt; }
}
