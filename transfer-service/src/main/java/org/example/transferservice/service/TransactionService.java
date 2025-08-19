package org.example.transferservice.service;


import org.example.transferservice.model.Transaction;
import org.example.transferservice.repository.ITransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class TransactionService {
    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
    private final ITransactionRepository transactionRepository;
    private static final float TAX_PERCENTAGE = 5.0f;

    public TransactionService(ITransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Mono<Transaction> save(Transaction transaction) {
        double tax = transaction.getAmount() * TAX_PERCENTAGE / 100;
        double totalAmount = transaction.getAmount() + tax;
        transaction.setTax(tax);
        transaction.setTotalAmount(totalAmount);
        transaction.setDateTimeProcessed(LocalDateTime.now());

        return transactionRepository.save(transaction)
                .doOnSuccess(response -> log.info("Transaction interbank processed: {}", transaction))
                .onErrorResume(error -> Mono.error(new RuntimeException(error.getMessage())));
    }
}
