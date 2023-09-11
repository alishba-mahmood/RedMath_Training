package org.example.transaction;

import org.example.account.AccountRepository;
import org.example.balance.BalanceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final BalanceService balanceService;

    public TransactionService(TransactionRepository tranRepository, AccountRepository accountRepository, BalanceService balanceService) {
        this.transactionRepository = tranRepository;
        this.accountRepository = accountRepository;
        this.balanceService = balanceService;
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }
    public void deleteTransByAccID(Long bal_id)
    {
        transactionRepository.deleteByAccountId(bal_id);
    }
    public Optional<Long> findAccIdInTranId(Long trans_id)
    {
        if(transactionRepository.findAccIdByTranId(trans_id) == null)
        {
            return null;
        }
        return transactionRepository.findAccIdByTranId(trans_id);
    }

    public Optional<Long> findIdByName(String login_name)
    {
        if(accountRepository.findIdByName(login_name) == null)
        {
            return null;
        }
        return accountRepository.findIdByName(login_name);
    }

    public List<Transaction> displayTransaction(Long id)
    {
        List<Transaction> transaction = transactionRepository.findByAccountId(id);
        return transaction;
    }
    public Optional<Transaction> CheckAndDisplayTransaction(Long id, String name)
    {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent())
        {
            if(name.equals("admin"))
            {
                return transaction;
            }
            Optional<Long> login_ac_id = findIdByName(name);
            Optional<Long> tran_acc_id = findAccIdInTranId(id);
            if( login_ac_id != null && tran_acc_id!=null)
            {
                Long user_id = login_ac_id.get();
                Long t_a_id = tran_acc_id.get();

                if(user_id == t_a_id)
                {
                    return transaction;
                }
            }
            return null;
        }
        return null;
    }
    public Long getMaxId(){
        System.out.println("\n\ngetting max id in service\n");

        Long id = transactionRepository.getMaxId();
        System.out.println(id);
        return id;
    }

    public Transaction createTransaction(Transaction transaction)
    {
        Long t_id = transactionRepository.getMaxId();
        if (transaction.getTransaction_id() != null && transactionRepository.existsById(transaction.getTransaction_id()))
        {
            return null;
        }
        transaction.setTransaction_id(t_id);
        Transaction newTransaction= transactionRepository.save(transaction);
        balanceService.updateBalanceByAccountId(newTransaction.getAccount_id(), newTransaction.getAmount(), newTransaction.getDB_CR(),newTransaction.getDate());
        return newTransaction;
    }
}
