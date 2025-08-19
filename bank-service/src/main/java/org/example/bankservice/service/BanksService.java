package org.example.bankservice.service;

import org.example.bankservice.dto.BankDTO;
import org.example.bankservice.model.Bank;
import org.example.bankservice.repository.IBanksRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BanksService {
  private final IBanksRepository repository;

  public BanksService(IBanksRepository repository) {
    this.repository = repository;
  }

  public Flux<Bank> getAll() {
    return repository.findAll();
  }

  public Mono<Bank> getById(Long bankId) {
    return repository
      .findById(bankId)
      .switchIfEmpty(Mono.error(new RuntimeException("Bank not found")));
  }

  public Mono<Bank> create(BankDTO bank) {
    Bank b = new Bank();
    b.setId(bank.getBankId());
    b.setName(bank.getName());
    b.setDescription(bank.getDescription());
    return repository.save(b);
  }

  public Mono<Void> deleteById(Long bankId) {
    return getById(bankId).flatMap(repository::delete);
  }
}
