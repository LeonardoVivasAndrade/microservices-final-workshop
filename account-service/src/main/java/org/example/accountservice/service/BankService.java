package org.example.accountservice.service;

import org.example.accountservice.dto.BankDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BankService {
  private final WebClient.Builder webClientBuilder;

  @Value("${bank-service.url}")
  private String bankServiceUrl;

  public BankService(final WebClient.Builder webClientBuilder) {
    this.webClientBuilder = webClientBuilder;
  }

  public Mono<BankDTO> getBank(Long bankId) {
    return webClientBuilder
      .build()
      .get()
      .uri(bankServiceUrl + "/" + bankId)
      .retrieve()
      .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new RuntimeException("Bank not found")))
      .bodyToMono(BankDTO.class);
  }
}
