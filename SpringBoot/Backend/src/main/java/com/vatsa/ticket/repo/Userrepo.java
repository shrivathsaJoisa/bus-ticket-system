package com.vatsa.ticket.repo;

import com.vatsa.ticket.model.Role;
import com.vatsa.ticket.model.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Userrepo extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);
    Optional<Users> findByPhone(String phone);

    boolean existsByPhone(String phone);

    List<Users> findByRole(Role role);
}
