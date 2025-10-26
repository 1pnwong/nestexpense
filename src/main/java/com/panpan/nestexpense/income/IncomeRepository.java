package com.panpan.nestexpense.income;

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
public interface IncomeRepository extends JpaRepository<Income, Long> {
    @Query(value = "SELECT * FROM income WHERE userID = :id", nativeQuery = true)
    List<Income> findAllByUserIdSql(@Param("id") Long userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE income SET source = :source, amount = :amount, dateIncome = :dateIncome WHERE incomeID = :incomeID", nativeQuery = true)
    int updateByIncomeIDSql(@Param("incomeID") Long incomeID, @Param("source") String source, @Param("amount") BigDecimal remark, @Param("dateIncome") LocalDateTime dateIncome);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM income WHERE income.incomeID = :incomeID", nativeQuery = true)
    int deleteByIncomeIDSql(@Param("incomeID") Long id);
}

