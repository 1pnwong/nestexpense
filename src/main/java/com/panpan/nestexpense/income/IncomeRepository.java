package com.panpan.nestexpense.income;

import com.panpan.nestexpense.expense.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    @Query(value = "SELECT * FROM income WHERE userID = :id", nativeQuery = true)
    List<Income> findAllByUserIdSql(@Param("id") Long userId);
}

