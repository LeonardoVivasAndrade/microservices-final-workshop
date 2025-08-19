package org.example.accountservice.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PostConstruct;
import org.example.accountservice.dto.MovementDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Iterator;

@Component
public class TransactionConsumer {
    @Value("${transaction.grpc.host}")
    private String grpcHost;
    @Value("${transaction.grpc.port}")
    private Integer grpcPort;

    private ManagedChannel channel;
    private TransactionServiceGrpc.TransactionServiceBlockingStub stub;

    @PostConstruct
    public void init() {
        channel = ManagedChannelBuilder.forAddress(grpcHost, grpcPort).usePlaintext().build();
        stub = TransactionServiceGrpc.newBlockingStub(channel);
    }

    public Flux<MovementDTO> getMovements(String accountNumber) {
        TransactionRequest request = TransactionRequest.newBuilder().setAccountNumber(accountNumber).build();

        return Flux.defer(() -> {
                    Iterator<TransactionResponse> responses = stub.getMovements(request);
                    return Flux.fromIterable(() -> responses);
                })
                .map(this::toMovementsDTO)
                .subscribeOn(Schedulers.boundedElastic());
    }

    private MovementDTO toMovementsDTO(TransactionResponse response) {
        return new MovementDTO(
                response.getVoucher(),
                response.getType(),
                response.getAccount(),
                response.getAmount(),
                response.getDateTime());
    }
}
