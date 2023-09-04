package org.example.transaction;
import java.util.Date;
import jakarta.persistence.*;

@Entity(name = "Transaction")
public class Transaction {
    @Id
    private Long transaction_id;
    private Long balance_id;

    private Long account_id;
    private String description;
    private Long amount;
    private Long DB_CR;
    @Temporal(TemporalType.DATE)
    @Column(name = "transaction_date")
    private Date transactionDate;

    public Long getId() {
        return transaction_id;
    }
    public void setId(Long id) {
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

    public Long getAmount() {
        return amount;
    }
    public void setAmount(Long amount) { this.amount = amount;    }

    public void setDB_CR(Long DB_CR) {
        this.DB_CR = DB_CR;
    }

    public Long getDB_CR() { return DB_CR;}

    public Date getDate() {
        return transactionDate;
    }

    public void setDate(Date date) {
        this.transactionDate = date;
    }
}
