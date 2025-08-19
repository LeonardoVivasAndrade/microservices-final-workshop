package org.example.bankservice.controller;

import org.example.bankservice.dto.BankDTO;
import org.example.bankservice.model.Bank;
import org.example.bankservice.service.BanksService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/banks")
public class BanksController {
  private final BanksService service;

  public BanksController(BanksService service) {
    this.service = service;
  }

  @GetMapping
  public Flux<Bank> getAll() {
    return service.getAll();
  }

  @GetMapping("/{bankId}")
  public Mono<Bank> getById(@PathVariable Long bankId) {
    return service.getById(bankId);
  }

  @PostMapping
  public Mono<Bank> create(@RequestBody BankDTO bank){
    return service.create(bank);
  }

  @PutMapping
  public Mono<Bank> modify(@RequestBody BankDTO bank){
    return service.create(bank);
  }

  @DeleteMapping("/{bankId}")
  public Mono<String> deleteById(@PathVariable Long bankId) {
    return service.deleteById(bankId).then(Mono.just("deleted ok"));
  }
}
