package org.example.accountservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("accounts")
public class Account {
  @Id
  @Column("id")
  private Long id;
  @Column("number")
  private String number;
  @Column("type")
  private String type;
  @Column("balance")
  private Double balance;
  @Column("bank_id")
  private Long bankId;
  @Column("created_date")
  private LocalDateTime createdDate;

  public Account() {
  }

  public Account(Long id, String number, String type, Double balance, Long bankId, LocalDateTime createdDate) {
    this.id = id;
    this.number = number;
    this.type = type;
    this.balance = balance;
    this.bankId = bankId;
    this.createdDate = createdDate;
  }

  public Account(Long id, String number, String type, Double balance, Long bankId) {
    this.id = id;
    this.number = number;
    this.type = type;
    this.balance = balance;
    this.bankId = bankId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  public Long getBankId() {
    return bankId;
  }

  public void setBankId(Long bankId) {
    this.bankId = bankId;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }
}
