package org.example.transactionservice.dto;

public class AccountDTO {
  private Long idAccount;
  private String number;
  private String type;
  private Double balance;
  private Long bankId;

  public AccountDTO() {
  }

  public AccountDTO(Long idAccount, String number, String type, Double balance, Long bankId) {
    this.idAccount = idAccount;
    this.number = number;
    this.type = type;
    this.balance = balance;
    this.bankId = bankId;
  }

  public Long getIdAccount() {
    return idAccount;
  }

  public void setIdAccount(Long idAccount) {
    this.idAccount = idAccount;
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
}
