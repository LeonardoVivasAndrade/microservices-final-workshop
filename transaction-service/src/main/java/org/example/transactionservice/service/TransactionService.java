package org.example.transactionservice.service;

import org.example.transactionservice.dto.TransactionDTO;
import org.example.transactionservice.model.Transaction;
import org.example.transactionservice.repository.ITransactionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionService {
    private final ITransactionRepository transactionRepository;
    private final AccountService accountService;
    private final TransactionPublisher publisher;

    public TransactionService(ITransactionRepository transactionRepository, AccountService accountService, TransactionPublisher publisher) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.publisher = publisher;
    }

    public Flux<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    private Mono<Transaction> save(Transaction t) {
        return transactionRepository.save(t)
                .onErrorResume(error -> Mono.error(new RuntimeException(error.getMessage())));
    }

    public Mono<Transaction> create(TransactionDTO transaction) {
        String sourceAccount = transaction.getSourceAccount();
        String destinationAccount = transaction.getDestinationAccount();

        return accountService.getAccountByNumber(sourceAccount)
                .flatMap(accountA -> {
                    if (accountA.getBalance() < transaction.getAmount()) {
                        return Mono.error(new RuntimeException("Insufficient balance"));
                    }

                    return accountService.getAccountByNumber(destinationAccount)
                            .flatMap(accountB -> {
                                String voucher = UUID.randomUUID().toString().replace("-", "").substring(21);
                                LocalDateTime now = LocalDateTime.now();
                                Transaction withdrawal = new Transaction(null, voucher, "withdrawal", sourceAccount, destinationAccount, transaction.getAmount(), now);

                                return save(withdrawal)
                                        .flatMap(savedWithdrawal -> {
                                            Transaction deposit = new Transaction(null, voucher, "deposit", sourceAccount, destinationAccount, transaction.getAmount(), now);
                                            if (accountA.getBankId().equals(accountB.getBankId())) {
                                                return save(deposit).thenReturn(savedWithdrawal);
                                            } else {
                                                return publisher.publishTransactionInterbank(deposit)
                                                        .thenReturn(savedWithdrawal);
                                            }
                                        });
                            });
                });
    }
}
