package org.example.transaction;

import org.example.account.AccountRepository;
import org.example.balance.BalanceService;
import org.example.user.Users;
import org.example.user.UsersService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final BalanceService balanceService;

    private final UsersService usersService;

    public TransactionService(TransactionRepository tranRepository, AccountRepository accountRepository, BalanceService balanceService, UsersService usersService) {
        this.transactionRepository = tranRepository;
        this.accountRepository = accountRepository;
        this.balanceService = balanceService;
        this.usersService = usersService;
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
        if(transactionRepository.findAccIdByTranId(trans_id).isEmpty())
        {
            return Optional.empty();
        }
        return transactionRepository.findAccIdByTranId(trans_id);
    }

    public Optional<Long> findIdByName(String login_name)
    {
        if(accountRepository.findIdByName(login_name).isEmpty())
        {
            return Optional.empty();
        }
        return accountRepository.findIdByName(login_name);
    }

    public Optional<List<Transaction>> displayTransactionsOfAccount(Long id, String loggedInData)
    {
        if (loggedInData.equals("admin"))
        {
            return Optional.ofNullable(transactionRepository.findByAccountId(id));
        }
        else
        {
            Users user = usersService.findByUserName(loggedInData);
            Long loggedId = user.getAccount_id();
            if(loggedId.equals(id))
            {
                return Optional.ofNullable(transactionRepository.findByAccountId(id));
            }
            return Optional.empty();
        }
    }
    public Optional<Transaction> checkAndDisplayTransaction(Long id, String name)
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
            if( login_ac_id.isPresent() && tran_acc_id.isPresent())
            {
                Long user_id = login_ac_id.get();
                Long t_a_id = tran_acc_id.get();

                if(user_id.equals(t_a_id))
                {
                    return transaction;
                }
            }
            return Optional.empty();
        }
        return Optional.empty();
    }
    public Long getMaxId(){

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
