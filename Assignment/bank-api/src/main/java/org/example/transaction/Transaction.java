package org.example.transaction;
import java.time.LocalDate;
import jakarta.persistence.*;

@Entity(name = "Transaction")
public class Transaction {
    @Id
    private Long transaction_id;
    private Long balance_id;
    private Long account_id;
    private String description;
    private int amount;
    private String DB_CR;
    private LocalDate date;

    public Long getTransaction_id() {
        return transaction_id;
    }
    public void setTransaction_id(Long id) {
        this.transaction_id = id;
    }
    public Long getBalance_id() {
        return balance_id;
    }
    public void setBalance_id(Long balance_id) {
        this.balance_id = balance_id;
    }

    public Long getAccount_id() {
        return account_id;
    }
    public void setAccount_id(Long account_id) {
        this.account_id = account_id;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return this.description;
    }

    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) { this.amount = amount;    }

    public void setDB_CR(String DB_CR) {
        this.DB_CR = DB_CR;
    }

    public String getDB_CR() { return DB_CR;}

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
