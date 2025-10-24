package com.panpan.nestexpense.goal;

import com.panpan.nestexpense.budget.Budget;
import com.panpan.nestexpense.expense.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    @Query(value = "SELECT * FROM goal WHERE userID = :id", nativeQuery = true)
    List<Goal> findAllByUserIdSql(@Param("id") Long userId);
}

