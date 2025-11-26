package com.vatsa.ticket.dto;

import java.math.BigDecimal;

public class RouteDto {

    private Long id;
    private String start;
    private String stop;
    private BigDecimal fare;

    public RouteDto() {
    }

    public RouteDto(Long id, String start, String stop, BigDecimal fare) {
        this.id = id;
        this.start = start;
        this.stop = stop;
        this.fare = fare;
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
