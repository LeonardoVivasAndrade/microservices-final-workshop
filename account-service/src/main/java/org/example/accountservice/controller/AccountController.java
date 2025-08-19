package org.example.accountservice.controller;

import org.example.accountservice.dto.AccountDTO;
import org.example.accountservice.dto.MovementDTO;
import org.example.accountservice.model.Account;
import org.example.accountservice.service.AccountService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
  private final AccountService service;

  public AccountController(AccountService service) {
    this.service = service;
  }

  @GetMapping
  public Flux<Account> getAll() {
    return service.getAll();
  }

  @GetMapping("/{accountId}")
  public Mono<Account> getById(@PathVariable Long accountId) {
    return service.getById(accountId);
  }

  @GetMapping("/account/{accountNumber}")
  public Mono<Account> getByNumber(@PathVariable String accountNumber) {
    return service.getByNumber(accountNumber);
  }

  @PostMapping
  public Mono<Account> create(@RequestBody AccountDTO accountDTO){
    return service.create(accountDTO);
  }

  @PutMapping
  public Mono<Account> update(@RequestBody AccountDTO accountDTO){
    return service.create(accountDTO);
  }

  @DeleteMapping("/{accountId}")
  public Mono<String> deleteById(@PathVariable Long accountId) {
    return service.deleteById(accountId).then(Mono.just("deleted ok"));
  }

  @GetMapping("/movements/account/{accountNumber}")
  public Flux<MovementDTO> getMovements(@PathVariable String accountNumber) {
    return service.getMovements(accountNumber);
  }
}
