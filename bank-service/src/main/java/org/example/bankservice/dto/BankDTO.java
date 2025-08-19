package org.example.bankservice.dto;

public class BankDTO {
  private Long bankId;
  private String name;
  private String description;

  public BankDTO() {
  }

  public BankDTO(Long bankId, String name, String description) {
    this.bankId = bankId;
    this.name = name;
    this.description = description;
  }

  public Long getBankId() {
    return bankId;
  }

  public void setBankId(Long bankId) {
    this.bankId = bankId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}