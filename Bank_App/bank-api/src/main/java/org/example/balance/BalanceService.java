package org.example.balance;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BalanceService {

    private final BalanceRepository balanceRepository;

    public BalanceService(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    public List<Balance> findAll() {
        return balanceRepository.findAll();
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

    public void updateBalanceByAccountId(Long id, int amount, String cr_db) {

        Balance updateBalance = balanceRepository.findByAccountId(id);
        if(cr_db.equals("credit"))
        {
            amount = amount + updateBalance.getAmount();
        }
        else {
            if(amount> updateBalance.getAmount())
            {
                amount= updateBalance.getAmount();
            }
            else {
                amount = amount - updateBalance.getAmount();
            }


        }
        balanceRepository.updateBalanceByAccountId(id,amount);
    }
//
//    public Balance create(Balance balance)
//    {
//        if (balance.getId() != null && balanceRepository.existsById(balance.getId()))
//        {
//            return null;
//        }
//        balance.setId(System.currentTimeMillis());
//        return balanceRepository.save(balance);
//    }
//    public Balance findByAccountId(Long acc_id)
//    {
//        return balanceRepository.findByAccountId(acc_id);
//    }
//
//    public void deleteByAccountId(Long acc_id)
//    {
//        balanceRepository.deleteByAccountId(acc_id);
//    }
//
//    public void updateBalanceByAccountId(Long id, Long amount, Long cr_db) {
//
//        Balance updateBalance = balanceRepository.findByAccountId(id);
//        if(cr_db == 1)
//        {
//            amount = amount + updateBalance.getAmount();
//        }
//        else {
//            amount = amount - updateBalance.getAmount();
//        }
//        balanceRepository.updateBalanceByAccountId(id,amount);
//    }
//
//    public Optional<Balance> findById(Long id) {
//
//        Optional<Balance> balance =  balanceRepository.findById(id);
//
//        if(balance.isPresent())
//        {
//            return balance;
//        }
//        else
//        {
//            return null;
//        }
//    }
}
