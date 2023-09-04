package org.example.balance;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "Balance")
public class Balance {
    @Id
    private Long balance_id;

    private Long account_id;
    private Long amount;
    private Long DB_CR;
    private Date date;

    public Long getId() {
        return balance_id;
    }
    public Long getAccount_id() {
        return account_id;
    }
    public void setAccount_id(Long id) {
        this.account_id = id;
    }

    public void setId(Long BalanceId) {
        this.balance_id = BalanceId;
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
        return date;
    }

    public void setDate(Date Date) {
        this.date = Date;
    }
}
