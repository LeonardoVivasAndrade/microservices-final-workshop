package org.example.transactionservice.controller;

import org.example.transactionservice.dto.TransactionDTO;
import org.example.transactionservice.model.Transaction;
import org.example.transactionservice.service.TransactionService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
  private final TransactionService service;

  public TransactionController(TransactionService service) {
    this.service = service;
  }

  @GetMapping
  public Flux<Transaction> getAll() {
    return service.getAll();
  }

  @PostMapping
  public Mono<Transaction> create(@RequestBody TransactionDTO transactionDTO){
    return service.create(transactionDTO);
  }
}
