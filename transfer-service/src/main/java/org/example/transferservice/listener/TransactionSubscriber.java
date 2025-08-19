package org.example.transferservice.listener;

import org.example.transferservice.model.Transaction;
import org.example.transferservice.service.TransactionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionSubscriber {
    private final TransactionService transactionService;

    public TransactionSubscriber(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RabbitListener(queues = "${transfer.mq.queue}")
    public void receiveCart(Transaction transaction) {
        transactionService.save(transaction)
                .subscribe(t -> {
                }, err -> System.err.println("Failed to process interbank transaction: " + err.getMessage()));
    }
}
