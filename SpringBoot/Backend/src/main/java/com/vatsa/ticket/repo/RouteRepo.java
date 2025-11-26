package com.vatsa.ticket.repo;

import com.vatsa.ticket.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepo extends JpaRepository<Route , Long> {
}
