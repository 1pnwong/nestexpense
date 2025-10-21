package com.panpan.nestexpense.expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query(value = "SELECT * FROM expense WHERE userID = :id", nativeQuery = true)
    List<Expense> findAllByUserIdSql(@Param("id") Long userId);
}

