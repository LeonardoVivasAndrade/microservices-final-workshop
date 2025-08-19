package org.example.transferservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("transactions")
public class Transaction {
    @Id
    @Column("id")
    private Long id;
    @Column("voucher")
    private String voucher;
    @Column("type")
    private String type;
    @Column("source_account")
    private String sourceAccount;
    @Column("destination_account")
    private String destinationAccount;
    @Column("tax")
    private Double tax;
    @Column("amount")
    private Double amount;
    @Column("total_amount")
    private Double totalAmount;
    @Column("date_time")
    private LocalDateTime dateTime;
    @Column("date_time_processed")
    private LocalDateTime dateTimeProcessed;

    public Transaction() {
    }

    public Transaction(Long id, String voucher, String type, String sourceAccount, String destinationAccount,
                       Double tax, Double amount, Double totalAmount, LocalDateTime dateTime, LocalDateTime dateTimeProcessed) {
        this.id = id;
        this.voucher = voucher;
        this.type = type;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.tax = tax;
        this.amount = amount;
        this.totalAmount = totalAmount;
        this.dateTime = dateTime;
        this.dateTimeProcessed = dateTimeProcessed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getDateTimeProcessed() {
        return dateTimeProcessed;
    }

    public void setDateTimeProcessed(LocalDateTime dateTimeProcessed) {
        this.dateTimeProcessed = dateTimeProcessed;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", voucher='" + voucher + '\'' +
                ", type='" + type + '\'' +
                ", sourceAccount='" + sourceAccount + '\'' +
                ", destinationAccount='" + destinationAccount + '\'' +
                ", tax=" + tax +
                ", amount=" + amount +
                ", totalAmount=" + totalAmount +
                ", dateTime=" + dateTime +
                ", dateTimeProcessed=" + dateTimeProcessed +
                '}';
    }
}
