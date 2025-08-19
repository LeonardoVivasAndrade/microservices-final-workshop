package org.example.accountservice.dto;

public class MovementDTO {
    private String voucher;
    private String type;
    private String account;
    private Double amount;
    private String dateTime;

    public MovementDTO() {
    }

    public MovementDTO(String voucher, String type, String account, Double amount, String dateTime) {
        this.voucher = voucher;
        this.type = type;
        this.account = account;
        this.amount = amount;
        this.dateTime = dateTime;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
