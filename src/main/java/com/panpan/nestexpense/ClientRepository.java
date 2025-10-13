package com.panpan.nestexpense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    //find by Email
    Client findByEmail(String email);
}

