package com.panpan.nestexpense.client;

import com.panpan.nestexpense.expense.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    //find by Email
    Client findByEmail(String email);
    @Query(value = "SELECT * FROM client WHERE userID = :id", nativeQuery = true)
    List<Client> findAllByUserIdSql(@Param("id") Long userId);
}

