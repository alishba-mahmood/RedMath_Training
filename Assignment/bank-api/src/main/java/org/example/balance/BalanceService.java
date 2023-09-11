package org.example.balance;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BalanceService {

    private final BalanceRepository balanceRepository;

    public BalanceService(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    public List<Balance> findAll() {
        return balanceRepository.findAll();
    }

    public Balance displayBalance(Long id)
    {
        Balance balance = balanceRepository.findByAccountId(id);
        return balance;
    }
    public void createBalance(LocalDate date, int amount, String DB_CR, Long account_id) {
        System.out.println("in create balance service------------------------------------------------");
        Balance bal = new Balance();
        bal.setAccount_id(account_id);
        bal.setAmount(amount);
        bal.setDate(date);
        bal.setDB_CR(DB_CR);
        balanceRepository.save(bal);
    }
    public void deleteBalanceByAccID(Long acc_id)
    {
        balanceRepository.deleteByAccountId(acc_id);
    }

    public void updateBalanceByAccountId(Long id, int amount, String cr_db,LocalDate date) {
        System.out.println("balance updated service ------------------------------------------------");

        Balance updateBalance = balanceRepository.findByAccountId(id);
        if(cr_db.equals("credit"))
        {
            amount = amount + updateBalance.getAmount();
            System.out.println("new amount +"+amount);
        }
        else {
            if(amount> updateBalance.getAmount())
            {
                amount= updateBalance.getAmount();
                System.out.println("new amount +"+amount);
            }
            else {
                amount = updateBalance.getAmount() - amount;
                System.out.println("new amount +"+amount);
            }


        }
        Balance bal = balanceRepository.findByAccountId(id);
        bal.setAmount(amount);
        bal.setDate(date);
        balanceRepository.save(bal);
    }
}
