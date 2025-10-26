package com.panpan.nestexpense.budget;

import com.panpan.nestexpense.expense.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    @Query(value = "SELECT * FROM budget WHERE userID = :id", nativeQuery = true)
    List<Budget> findAllByUserIdSql(@Param("id") Long userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE budget SET amountSpent = :amountSpent, category = :category WHERE budgetID = :budgetID", nativeQuery = true)
    int updateByBudgetIDSql(@Param("budgetID") Long budgetID, @Param("amountSpent") BigDecimal amountSpent, @Param("category") String category);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM budget WHERE budget.budgetID = :budgetID", nativeQuery = true)
    int deleteByBudgetIDSql(@Param("budgetID") Long id);
}
