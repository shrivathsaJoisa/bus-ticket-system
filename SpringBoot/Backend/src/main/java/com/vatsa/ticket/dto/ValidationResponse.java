package com.vatsa.ticket.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ValidationResponse {

    private String status; // VALID or INVALID or EXPIRED
    private String message;

    private Long ticketId;
    private String passengerName;
    private String start;
    private String stop;
    private BigDecimal fare;

    private LocalDateTime validatedAt;

    public ValidationResponse() {
    }

    public ValidationResponse(String status, String message, Long ticketId,
                              String passengerName, String start, String stop,
                              BigDecimal fare, LocalDateTime validatedAt) {
        this.status = status;
        this.message = message;
        this.ticketId = ticketId;
        this.passengerName = passengerName;
        this.start = start;
        this.stop = stop;
        this.fare = fare;
        this.validatedAt = validatedAt;
    }

    // getters & setters...

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public BigDecimal getFare() {
        return fare;
    }

    public void setFare(BigDecimal fare) {
        this.fare = fare;
    }

    public LocalDateTime getValidatedAt() {
        return validatedAt;
    }

    public void setValidatedAt(LocalDateTime validatedAt) {
        this.validatedAt = validatedAt;
    }
}
