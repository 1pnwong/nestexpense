package com.panpan.nestexpense.budget;

import com.panpan.nestexpense.expense.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    @Query(value = "SELECT * FROM budget WHERE userID = :id", nativeQuery = true)
    List<Budget> findAllByUserIdSql(@Param("id") Long userId);
}
