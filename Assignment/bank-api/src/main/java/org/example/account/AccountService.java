package org.example.account;

import org.example.balance.BalanceService;
import org.example.transaction.TransactionService;
import org.example.user.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${bank.db.like.operator:%}")    //used to set default value of things
    private String likeOperator;
    private final AccountRepository repository;
    private final UsersService userService;
    private final BalanceService balanceService;
    private final TransactionService transactionService;
    public AccountService(AccountRepository rep, UsersService userService, BalanceService balanceService, TransactionService transactionService) {
        this.repository = rep;
        this.userService = userService;
        this.balanceService = balanceService;
        this.transactionService = transactionService;
    }

    public List<Account> findAll() {

        return repository.findAll();
    }
    public List<List<Object>> findAllData() {
        List<List<Object>> nestedLists = new ArrayList<>();
        nestedLists.add(new ArrayList<>(repository.findAll()));
        nestedLists.add(new ArrayList<>(balanceService.findAll()));
        nestedLists.add(new ArrayList<>(transactionService.findAll()));
        return nestedLists;
    }
    public boolean idFound(Long id) {
        return repository.existsById(id);
    }
    public Optional<Long> findIdByName(String login_name)
    {
        if(repository.findIdByName(login_name).isEmpty())
        {
            return Optional.empty();
        }
        return repository.findIdByName(login_name);
    }
    public Optional<Account> checkAndDisplay(Long id, String loggedInData)
    {
        if (loggedInData.equals("admin"))
        {
            return repository.findById(id);
        }
        else
        {
            Optional<Long> getId = findIdByName(loggedInData);
            if(getId.isPresent())
            {
                Long loggedId = getId.get();
                if(loggedId.equals(id))
                {
                    return repository.findById(id);
                }
            }
            return Optional.empty();
        }

    }
    public Account create(Account account)
    {
        if (account.getAccount_id() != null && repository.existsById(account.getAccount_id()))
        {
            logger.warn("Account already exist");
            return null;
        }
        Account newAccount= repository.save(account);
        userService.createUser(newAccount.getAccount_id(),newAccount.getName(),"{noop}"+newAccount.getName(),"USER","ACTIVE",newAccount.getAccount_id());
        balanceService.createBalance(LocalDate.now(), 0, "debit", newAccount.getAccount_id());
        return newAccount;
    }

    public Optional<Account> checkAndUpdate(Long id, Account account, String name)
    {
        Optional<Long> login_id = findIdByName(name);
        if(repository.existsById(id))
        {
            if(name.equals("admin"))
            {

                Optional<Account> foundAccount = repository.findById(id);
                if (foundAccount.isPresent())
                {
                    Account newAccount = foundAccount.get();
                    newAccount.setAccount_id(account.getAccount_id());
                    newAccount.setName(account.getName());
                    newAccount.setEmail(account.getEmail());
                    newAccount.setAddress(account.getAddress());
                    repository.save(newAccount);
                    userService.UpdateUser(id,newAccount);
                    return Optional.of(newAccount);
                }

            }
            else {
                if(login_id.isPresent())
                {
                    Long matched_id = login_id.get();
                    if(matched_id.equals(id))
                    {
                        Optional<Account> foundAccount = repository.findById(id);
                        if (foundAccount.isPresent())
                        {
                            Account newAccount = foundAccount.get();
                            newAccount.setAccount_id(account.getAccount_id());
                            newAccount.setName(account.getName());
                            newAccount.setEmail(account.getEmail());
                            newAccount.setAddress(account.getAddress());
                            repository.save(newAccount);
                            return Optional.of(newAccount);
                        }

                    }
                    return Optional.empty();
                }
                return Optional.empty();

            }

        }
        return Optional.empty();
    }
    public boolean checkAndDelete(Long id)
    {
        if(!repository.existsById(id)){
            return false;
        }
        transactionService.deleteTransByAccID(id);
        balanceService.deleteBalanceByAccID(id);
        userService.deleteUserByAccID(id);
        repository.deleteById(id);
        return true;
    }

    public List<Account> getAccountsByNameLike(String name){
        return repository.findByNameLike(likeOperator+name+likeOperator);
    }

}
