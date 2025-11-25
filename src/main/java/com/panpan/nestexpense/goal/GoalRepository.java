package com.panpan.nestexpense.goal;

import com.panpan.nestexpense.budget.Budget;
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
public interface GoalRepository extends JpaRepository<Goal, Long> {
    //select all data from userID
    @Query(value = "SELECT * FROM goal WHERE userID = :id", nativeQuery = true)
    List<Goal> findAllByUserIdSql(@Param("id") Long userId);

    //update current record by expense ID
    @Modifying
    @Transactional
    @Query(value = "UPDATE goal SET goal = :goal, currentBalance = :currentBalance, amountReachGoal = :amountReachGoal, purpose = :purpose WHERE goalID = :goalID", nativeQuery = true)
    int updateByGoalIDSql(@Param("goalID") Long expenseID, @Param("goal") BigDecimal goal, @Param("currentBalance") BigDecimal currentBalance, @Param("amountReachGoal") BigDecimal amountReachGoal, @Param("purpose") String purpose);

    //delete current record by expense ID
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM goal WHERE goal.goalID = :goalID", nativeQuery = true)
    int deleteByGoalIDSql(@Param("goalID") Long id);
}

