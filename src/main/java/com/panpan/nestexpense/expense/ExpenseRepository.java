package com.panpan.nestexpense.expense;

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
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    //select all data from userID
    @Query(value = "SELECT * FROM expense WHERE userID = :id", nativeQuery = true)
    List<Expense> findAllByUserIdSql(@Param("id") Long userId);

    //update current record by expense ID
    @Modifying
    @Transactional
    @Query(value = "UPDATE expense SET sent_to_Where = :sentToWhere, amount = :amount, date_expense = :dateExpense, category = :category WHERE expenseID = :expenseID", nativeQuery = true)
    int updateByExpenseIDSql(@Param("expenseID") Long expenseID, @Param("sentToWhere") String sentToWhere, @Param("amount") BigDecimal remark, @Param("dateExpense") LocalDateTime dateExpense, @Param("category") String category);

    //delete current record by expense ID
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM expense WHERE expense.expenseID = :expenseID", nativeQuery = true)
    int deleteByExpenseIDSql(@Param("expenseID") Long id);
}

