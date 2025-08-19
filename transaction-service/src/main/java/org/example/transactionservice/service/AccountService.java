package org.example.transactionservice.service;

import org.example.transactionservice.dto.AccountDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AccountService {
  private final WebClient.Builder webClientBuilder;

  @Value("${account-service.url}")
  private String bankServiceUrl;

  public AccountService(final WebClient.Builder webClientBuilder) {
    this.webClientBuilder = webClientBuilder;
  }

  public Mono<AccountDTO> getAccountByNumber(String accountNumber) {
    return webClientBuilder
      .build()
      .get()
      .uri(bankServiceUrl + "/account/" + accountNumber)
      .retrieve()
      .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new RuntimeException("Account not found")))
      .bodyToMono(AccountDTO.class);
  }
}
