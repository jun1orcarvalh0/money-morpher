package com.ada.moneymorpher.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    public List<Transaction> findAllByOrderByCreatedAtDesc();

    @Query("select sum(case when t.transactionType = 'CASHIN' then t.BRLValue else -t.BRLValue end) from Transaction t")
    public BigDecimal getBRLBalance();

    @Query("select sum(case when t.transactionType = 'CASHIN' then t.USDValue else -t.USDValue end) from Transaction t")
    public BigDecimal getUSDBalance();

    @Query("select sum(case when t.transactionType = 'CASHIN' then t.EURValue else -t.EURValue end) from Transaction t")
    public BigDecimal getEURBalance();
}
