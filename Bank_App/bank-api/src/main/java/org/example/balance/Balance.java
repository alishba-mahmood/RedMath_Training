package org.example.balance;
import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "Balance")
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long balance_id;

    private Long account_id;
    private int amount;
    private String DB_CR;
    private LocalDate date;

    public Balance(){}

    public Long getBalance_id() {
        return balance_id;
    }

    public void setBalance_id(Long balance_id)
    {
        this.balance_id = balance_id;
    }
    public Long getAccount_id() {
        return account_id;
    }
    public void setAccount_id(Long id) {
        this.account_id = id;
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