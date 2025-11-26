package com.vatsa.ticket.repo;

import com.vatsa.ticket.model.Ticket;
import com.vatsa.ticket.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepo extends JpaRepository<Ticket, Long> {

    List<Ticket> findByUser(Users user);

    Optional<Ticket> findByQrToken(String qrToken);

    List<Ticket> findByRoute_Id(Long routeId);
}