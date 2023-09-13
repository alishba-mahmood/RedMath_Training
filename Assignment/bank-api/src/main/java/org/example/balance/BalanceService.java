package org.example.balance;

import org.example.user.Users;
import org.example.user.UsersService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BalanceService {

    private final BalanceRepository balanceRepository;
    private final UsersService usersService;

    public BalanceService(BalanceRepository balanceRepository, UsersService usersService) {
        this.balanceRepository = balanceRepository;
        this.usersService = usersService;
    }

    public List<Balance> findAll() {
        return balanceRepository.findAll();
    }

    public Balance displayBalance(Long id, String loggedInData)
    {
        if (loggedInData.equals("admin"))
        {
            return balanceRepository.findByAccountId(id);
        }
        else
        {
            Users user = usersService.findByUserName(loggedInData);
            Long loggedId = user.getAccount_id();
            if(loggedId.equals(id))
            {
                return balanceRepository.findByAccountId(id);
            }
            return null;
        }
    }
    public void createBalance(LocalDate date, int amount, String DB_CR, Long account_id) {
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
                amount = updateBalance.getAmount() - amount;
            }


        }
        Balance bal = balanceRepository.findByAccountId(id);
        bal.setAmount(amount);
        bal.setDate(date);
        balanceRepository.save(bal);
    }
}
