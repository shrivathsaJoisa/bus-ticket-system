package com.vatsa.ticket.service;

import com.vatsa.ticket.dto.RouteDto;
import com.vatsa.ticket.model.Route;
import com.vatsa.ticket.repo.RouteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteService {

    @Autowired
    private final RouteRepo routeRepository;

    public RouteService(RouteRepo routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<RouteDto> getAllRoutes() {
        return routeRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public RouteDto getRouteById(Long id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Route not found with id: " + id));
        return toDto(route);
    }

    public RouteDto createRoute(RouteDto dto) {
        Route route = new Route();
        route.setStart(dto.getStart());
        route.setStop(dto.getStop());
        route.setFare(dto.getFare());
        Route saved = routeRepository.save(route);
        return toDto(saved);
    }

    public RouteDto updateRoute(Long id, RouteDto dto) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Route not found with id: " + id));

        route.setStart(dto.getStart());
        route.setStop(dto.getStop());
        route.setFare(dto.getFare());

        Route updated = routeRepository.save(route);
        return toDto(updated);
    }

    public void deleteRoute(Long id) {
        if (!routeRepository.existsById(id)) {
            throw new RuntimeException("Route not found with id: " + id);
        }
        routeRepository.deleteById(id);
    }

    private RouteDto toDto(Route route) {
        return new RouteDto(
                route.getId(),
                route.getStart(),
                route.getStop(),
                route.getFare()
        );
    }
}
