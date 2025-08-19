package org.example.bankservice.controller;

import org.example.bankservice.dto.BankDTO;
import org.example.bankservice.model.Bank;
import org.example.bankservice.service.BanksService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(BanksController.class)
class BanksControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BanksService banksService;

    private Bank bank1;
    private Bank bank2;

    @BeforeEach
    void setUp() {
        bank1 = new Bank();
        bank1.setId(1L);
        bank1.setName("Bank A");
        bank1.setDescription("Description A");

        bank2 = new Bank();
        bank2.setId(2L);
        bank2.setName("Bank B");
        bank2.setDescription("Description B");
    }

    @Test
    void getAll() {
        when(banksService.getAll()).thenReturn(Flux.just(bank1, bank2));

        webTestClient.get().uri("/api/banks")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].id").isEqualTo(bank1.getId())
                .jsonPath("$[0].name").isEqualTo(bank1.getName())
                .jsonPath("$[1].id").isEqualTo(bank2.getId())
                .jsonPath("$[1].name").isEqualTo(bank2.getName());
    }

    @Test
    void getById_whenBankExists() {
        when(banksService.getById(1L)).thenReturn(Mono.just(bank1));

        webTestClient.get().uri("/api/banks/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(bank1.getId())
                .jsonPath("$.name").isEqualTo(bank1.getName())
                .jsonPath("$.description").isEqualTo(bank1.getDescription());
    }

    @Test
    void getById_whenBankDoesNotExist() {
        when(banksService.getById(99L)).thenReturn(Mono.error(new RuntimeException("Bank not found")));

        webTestClient.get().uri("/api/banks/99")
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void create() {
        BankDTO newBankDto = new BankDTO(null, "New Bank", "A new bank");
        when(banksService.create(any(BankDTO.class))).thenReturn(Mono.just(bank1));

        webTestClient.post().uri("/api/banks")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(newBankDto), BankDTO.class)
                .exchange()
                .expectStatus().isOk() // Note: Should ideally be 201 Created for POST
                .expectBody()
                .jsonPath("$.id").isEqualTo(bank1.getId())
                .jsonPath("$.name").isEqualTo(bank1.getName());
    }

    @Test
    void modify() {
        // Note: This test validates the current behavior where modify() calls create().
        // This is likely a bug and should be corrected.
        BankDTO updatedBankDto = new BankDTO(1L, "Updated Bank A", "Updated description");
        when(banksService.create(any(BankDTO.class))).thenReturn(Mono.just(bank1));

        webTestClient.put().uri("/api/banks")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updatedBankDto), BankDTO.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(bank1.getId());
    }

    @Test
    void deleteById() {
        when(banksService.deleteById(1L)).thenReturn(Mono.empty());

        webTestClient.delete().uri("/api/banks/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("deleted ok");
    }
}