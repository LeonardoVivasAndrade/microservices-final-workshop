package org.example.transferservice.repository;

import org.example.transferservice.model.Transaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ITransactionRepository extends ReactiveCrudRepository<Transaction, Long> {
}