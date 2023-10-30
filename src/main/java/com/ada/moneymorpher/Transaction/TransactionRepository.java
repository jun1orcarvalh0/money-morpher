package com.ada.moneymorpher.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByOrderByCreatedAtDesc();

    @Query("select t from Transaction t inner join t.profile p where p.username = :username order by t.createdAt DESC")
    List<Transaction> findByProfileUsernameOrdered(@Param("username") String username);

    @Query("select sum(case when t.transactionType = 'CASHIN' then t.BRLValue else -t.BRLValue end) from Transaction t where t.profile.username = :username")
    BigDecimal getBRLBalance(@Param("username") String username);

    @Query("select sum(case when t.transactionType = 'CASHIN' then t.USDValue else -t.USDValue end) from Transaction t where t.profile.username = :username")
    BigDecimal getUSDBalance(@Param("username") String username);

    @Query("select sum(case when t.transactionType = 'CASHIN' then t.EURValue else -t.EURValue end) from Transaction t where t.profile.username = :username")
    BigDecimal getEURBalance(@Param("username") String username);

    Optional<Transaction> findByUuid(UUID uuid);
}
