package com.vatsa.ticket.repo;

import com.vatsa.ticket.model.Users;
import com.vatsa.ticket.model.ValidationLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ValidationLogRepo  extends JpaRepository<ValidationLog, Long> {

    List<ValidationLog> findByCollector(Users collector);
    List<ValidationLog> findByTicketId(Long ticketId);

    // List<ValidationLog> findByCollector(User collector);  // keep if needed later
}
