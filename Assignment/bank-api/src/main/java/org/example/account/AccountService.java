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
    @Value("${news.page.min:0}")
    public int pageMin = 0;

    @Value("${news.page.size.min:1}")
    public int pageSizeMin = 1;

    @Value("${news.page.size.max:100}")
    public int pageSizeMax = 100;

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
        if(!repository.existsById(id)){
            return false;
        }
        return true;
    }
    public Optional<Long> findIdByName(String login_name)
    {
        if(repository.findIdByName(login_name) == null)
        {
            return null;
        }
        return repository.findIdByName(login_name);
    }
    public Optional<Account> CheckAndDisplay(Long id)
    {
        Optional<Account> acc = repository.findById(id);
        if (acc.isPresent())
        {
            return acc;
        }
        return null;
    }
    public Account create(Account account)
    {
        System.out.println("in create accounts service------------------------------------------------");
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

    public Optional<Account> CheckAndUpdate(Long id, Account account, String name)
    {
        Optional<Long> login_id = findIdByName(name);
        if(repository.existsById(id))
        {
            if(name.equals("admin"))
            {
                System.out.println("\n\n");
                System.out.println("in update account service------------------------------------------------");

                Optional<Account> foundAccount = repository.findById(id);
                Account newAccount = foundAccount.get();
                newAccount.setAccount_id(account.getAccount_id());
                newAccount.setName(account.getName());
                newAccount.setEmail(account.getEmail());
                newAccount.setAddress(account.getAddress());
                repository.save(newAccount);
                userService.UpdateUser(id,newAccount);
                return Optional.of(newAccount);
            }
            else {
                if( login_id != null)
                {
                    Long matched_id = login_id.get();
                    if(matched_id == id)
                    {
                        Optional<Account> foundAccount = repository.findById(id);
                        Account newAccount = foundAccount.get();
                        newAccount.setAccount_id(account.getAccount_id());
                        newAccount.setName(account.getName());
                        newAccount.setEmail(account.getEmail());
                        newAccount.setAddress(account.getAddress());
                        repository.save(newAccount);
                        return Optional.of(newAccount);
                    }
                    return null;
                }
                return null;

            }

        }
        return null;
    }
    public boolean checkAndDelete(Long id)
    {
        if(!repository.existsById(id)){
            return false;
        }
        System.out.println("in delete accounts service------------------------------------------------");
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
