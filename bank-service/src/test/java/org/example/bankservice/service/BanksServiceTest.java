package org.example.bankservice.service;

import org.example.bankservice.dto.BankDTO;
import org.example.bankservice.model.Bank;
import org.example.bankservice.repository.IBanksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BanksServiceTest {

    @Mock
    private IBanksRepository repository;

    @InjectMocks
    private BanksService banksService;

    private Bank bank1;
    private Bank bank2;

    @BeforeEach
    void setUp() {
        bank1 = new Bank();
        bank1.setId(1L);
        bank1.setName("Bank One");
        bank1.setDescription("Description for Bank One");

        bank2 = new Bank();
        bank2.setId(2L);
        bank2.setName("Bank Two");
        bank2.setDescription("Description for Bank Two");
    }

    @Test
    void getAll_shouldReturnAllBanks() {
        when(repository.findAll()).thenReturn(Flux.fromIterable(List.of(bank1, bank2)));

        Flux<Bank> result = banksService.getAll();

        StepVerifier.create(result)
                .expectNext(bank1)
                .expectNext(bank2)
                .verifyComplete();

        verify(repository).findAll();
    }

    @Test
    void getById_whenBankExists_shouldReturnBank() {
        when(repository.findById(1L)).thenReturn(Mono.just(bank1));

        Mono<Bank> result = banksService.getById(1L);

        StepVerifier.create(result)
                .expectNextMatches(bank -> bank.getId().equals(1L) && bank.getName().equals("Bank One"))
                .verifyComplete();

        verify(repository).findById(1L);
    }

    @Test
    void getById_whenBankDoesNotExist_shouldThrowException() {
        when(repository.findById(anyLong())).thenReturn(Mono.empty());

        Mono<Bank> result = banksService.getById(99L);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Bank not found"))
                .verify();

        verify(repository).findById(99L);
    }

    @Test
    void create_shouldSaveAndReturnBank() {
        BankDTO bankDTO = new BankDTO(3L, "Bank Three", "New Bank Description");
        Bank newBank = new Bank();
        newBank.setId(bankDTO.getBankId());
        newBank.setName(bankDTO.getName());
        newBank.setDescription(bankDTO.getDescription());

        when(repository.save(any(Bank.class))).thenReturn(Mono.just(newBank));

        Mono<Bank> result = banksService.create(bankDTO);

        StepVerifier.create(result)
                .expectNextMatches(savedBank -> {
                    assertEquals(3L, savedBank.getId());
                    assertEquals("Bank Three", savedBank.getName());
                    return true;
                })
                .verifyComplete();

        ArgumentCaptor<Bank> bankCaptor = ArgumentCaptor.forClass(Bank.class);
        verify(repository).save(bankCaptor.capture());

        Bank capturedBank = bankCaptor.getValue();
        assertEquals(3L, capturedBank.getId());
        assertEquals("Bank Three", capturedBank.getName());
        assertEquals("New Bank Description", capturedBank.getDescription());
    }

    @Test
    void deleteById_whenBankExists_shouldComplete() {
        when(repository.findById(1L)).thenReturn(Mono.just(bank1));
        when(repository.delete(bank1)).thenReturn(Mono.empty());

        Mono<Void> result = banksService.deleteById(1L);

        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(1L);
        verify(repository).delete(bank1);
    }

    @Test
    void deleteById_whenBankDoesNotExist_shouldThrowException() {
        when(repository.findById(99L)).thenReturn(Mono.empty());

        Mono<Void> result = banksService.deleteById(99L);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Bank not found"))
                .verify();

        verify(repository).findById(99L);
        verify(repository, never()).delete(any(Bank.class));
    }
}