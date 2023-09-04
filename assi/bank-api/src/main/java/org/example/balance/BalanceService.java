package org.example.balance;


import org.springframework.stereotype.Service;

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

    public Balance create(Balance balance)
    {
        if (balance.getId() != null && balanceRepository.existsById(balance.getId()))
        {
            return null;
        }
        balance.setId(System.currentTimeMillis());
        return balanceRepository.save(balance);
    }
    public Balance findByAccountId(Long acc_id)
    {
       return balanceRepository.findByAccountId(acc_id);
    }

    public void deleteByAccountId(Long acc_id)
    {
        balanceRepository.deleteByAccountId(acc_id);
    }

    public void updateBalanceByAccountId(Long id, Long amount, Long cr_db) {

        Balance updateBalance = balanceRepository.findByAccountId(id);
        if(cr_db == 1)
        {
            amount = amount + updateBalance.getAmount();
        }
        else {
            amount = amount - updateBalance.getAmount();
        }
        balanceRepository.updateBalanceByAccountId(id,amount);
    }
}
