package com.vatsa.ticket.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "validation_logs")
public class ValidationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Which ticket was validated
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    // Who validated (collector)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "collector_id")
    private Users collector;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ValidationStatus status;

    @Column(name = "validated_at", nullable = false)
    private LocalDateTime validatedAt;

    public ValidationLog() {
    }

    public ValidationLog(Ticket ticket, Users collector,
                         ValidationStatus status, LocalDateTime validatedAt) {
        this.ticket = ticket;
        this.collector = collector;
        this.status = status;
        this.validatedAt = validatedAt;
    }

    // --- Getters & Setters ---
    public Long getId() {
        return id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Users getCollector() {
        return collector;
    }

    public void setCollector(Users collector) {
        this.collector = collector;
    }

    public ValidationStatus getStatus() {
        return status;
    }

    public void setStatus(ValidationStatus status) {
        this.status = status;
    }

    public LocalDateTime getValidatedAt() {
        return validatedAt;
    }

    public void setValidatedAt(LocalDateTime validatedAt) {
        this.validatedAt = validatedAt;
    }
}