package org.example.accountservice.service;

import org.example.accountservice.dto.AccountDTO;
import org.example.accountservice.dto.MovementDTO;
import org.example.accountservice.grpc.TransactionConsumer;
import org.example.accountservice.model.Account;
import org.example.accountservice.repository.IAccountRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class AccountService {
  private final IAccountRepository accountRepository;
  private final BankService bankService;
  private final TransactionConsumer transactionConsumer;

  public AccountService(IAccountRepository accountRepository, BankService bankService, TransactionConsumer transactionConsumer) {
    this.accountRepository = accountRepository;
      this.bankService = bankService;
      this.transactionConsumer = transactionConsumer;
  }

    public Flux<Account> getAll() {
        return accountRepository.findAll();
    }

    public Mono<Account> getById(Long accountId) {
        return accountRepository
                .findById(accountId)
                .switchIfEmpty(Mono.error(new RuntimeException("Account not found")));
    }

    public Mono<Account> getByNumber(String accountNumber) {
        return accountRepository
                .findByNumber(accountNumber)
                .switchIfEmpty(Mono.error(new RuntimeException("Account not found")));
    }

    public Mono<Account> create(AccountDTO a) {
        return bankService.getBank(a.getBankId())
                .switchIfEmpty(Mono.error(new RuntimeException("Bank id not exist: "+a.getBankId())))
                .flatMap(bankDTO -> accountRepository.save(new Account(a.getIdAccount(), a.getNumber(), a.getType(), a.getBalance(), a.getBankId(), LocalDateTime.now())))
                .onErrorResume(error -> Mono.error(new RuntimeException(error.getMessage())));
    }

    public Mono<Void> deleteById(Long accountId) {
        return getById(accountId).flatMap(accountRepository::delete);
    }

    public Flux<MovementDTO> getMovements(String accountNumber){
      return transactionConsumer.getMovements(accountNumber);
    }
}
