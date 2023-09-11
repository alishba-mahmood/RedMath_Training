package org.example.balance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {
    @Query(value = "SELECT * FROM Balance b WHERE b.account_id = :accountId", nativeQuery = true)
    Balance findByAccountId(Long accountId);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Balance b WHERE b.account_id = :accountId", nativeQuery = true)
    void deleteByAccountId(@Param("accountId") Long accountId);
    @Transactional
    @Modifying
    @Query(value = "UPDATE Balance b SET b.amount = :amount and b.date = :date WHERE b.account_id = :accountId", nativeQuery = true)
    void updateBalanceByAccountId(@Param("accountId") Long accountId, int amount,LocalDate date );

}
