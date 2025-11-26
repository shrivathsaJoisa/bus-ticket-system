package com.vatsa.ticket.dto;

public class TicketCreateRequest {

    private Long routeId;

    public TicketCreateRequest() {
    }

    public TicketCreateRequest(Long routeId) {
        this.routeId = routeId;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }
}
