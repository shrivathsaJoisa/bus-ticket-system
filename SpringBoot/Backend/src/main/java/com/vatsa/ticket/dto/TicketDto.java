package com.vatsa.ticket.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TicketDto {

    private Long ticketId;
    private Long routeId;
    private String start;
    private String stop;
    private BigDecimal fare;

    private String qrToken;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;
    private boolean validated;

    public TicketDto() {
    }

    public TicketDto(Long ticketId, Long routeId, String start, String stop,
                     BigDecimal fare, String qrToken,
                     LocalDateTime issuedAt, LocalDateTime expiresAt,
                     boolean validated) {
        this.ticketId = ticketId;
        this.routeId = routeId;
        this.start = start;
        this.stop = stop;
        this.fare = fare;
        this.qrToken = qrToken;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.validated = validated;
    }

    // getters & setters...

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
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

    public String getQrToken() {
        return qrToken;
    }

    public void setQrToken(String qrToken) {
        this.qrToken = qrToken;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }
}
