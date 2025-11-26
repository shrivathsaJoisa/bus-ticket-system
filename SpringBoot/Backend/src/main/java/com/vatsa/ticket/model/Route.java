package com.vatsa.ticket.model;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start", nullable = false)
    private String start;

    @Column(name = "stop", nullable = false)
    private String stop;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal fare;

    public Route() {
    }

    public Route(String start, String stop, BigDecimal fare) {
        this.start = start;
        this.stop = stop;
        this.fare = fare;
    }

    // --- Getters & Setters ---
    public Long getId() {
        return id;
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
}