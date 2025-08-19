package org.example.transactionservice.grpc;

import io.grpc.stub.StreamObserver;
import org.example.transactionservice.dto.MovementDTO;
import org.example.transactionservice.repository.ITransactionRepository;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class TransactionProvider extends TransactionServiceGrpc.TransactionServiceImplBase {
    private final ITransactionRepository repository;

    public TransactionProvider(ITransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void getMovements(TransactionRequest request, StreamObserver<TransactionResponse> responseObserver) {
        repository.findAllByAccountNumber(request.getAccountNumber())
                .map(this::toTransactionResponse)
                .subscribe(responseObserver::onNext, responseObserver::onError, responseObserver::onCompleted);
    }

    private TransactionResponse toTransactionResponse(MovementDTO movement) {
        return TransactionResponse.newBuilder()
                .setVoucher(movement.getVoucher())
                .setType(movement.getType())
                .setAccount(movement.getAccount())
                .setAmount(movement.getAmount())
                .setDateTime(movement.getDateTime().toString())
                .build();
    }
}
