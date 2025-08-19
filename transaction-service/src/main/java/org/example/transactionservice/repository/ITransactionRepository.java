package org.example.transactionservice.repository;

import org.example.transactionservice.dto.MovementDTO;
import org.example.transactionservice.model.Transaction;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ITransactionRepository extends ReactiveCrudRepository<Transaction, Long> {
    @Query("SELECT * FROM (SELECT voucher,source_account AS account,'withdrawal' " +
            "AS type,amount,date_time FROM transactions WHERE type = 'withdrawal' " +
            "UNION SELECT voucher, destination_account AS account, 'deposit' AS type, amount, date_time " +
            "FROM transactions WHERE type = 'deposit') WHERE account = :accountNumber ORDER BY date_time DESC")
    Flux<MovementDTO> findAllByAccountNumber(@Param("accountNumber") String accountNumber);
}