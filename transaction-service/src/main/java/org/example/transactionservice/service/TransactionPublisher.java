package org.example.transactionservice.service;

import org.example.transactionservice.model.Transaction;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TransactionPublisher {
    @Value("${transfer.mq.exchange}")
    private String transferExchange;
    @Value("${transfer.mq.routing-key}")
    private String transferRutingKey;
    private final RabbitTemplate rabbitTemplate;

    public TransactionPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public Mono<Void> publishTransactionInterbank(Transaction transaction) {
        return Mono.fromRunnable(() -> {
            rabbitTemplate.convertAndSend(transferExchange, transferRutingKey, transaction);
            System.out.println("Transaction interbank event published: " + transaction.toString());
        });
    }
}
