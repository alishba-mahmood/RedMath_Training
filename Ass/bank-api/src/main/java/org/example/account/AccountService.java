package org.example.account;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository repository;

    public AccountService(AccountRepository rep) {
        this.repository = rep;
    }

    public Account findByName(String name)
    {
        return repository.findByName(name);
    }
    public List<Account> findAll() {
        return repository.findAll();
    }

    public Optional<Account> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<Long> findIdByName(String login_name)
    {
        if(repository.findIdByName(login_name) == null)
        {
            return null;
        }
        return repository.findIdByName(login_name);
    }
    public Account create(Account account)
    {
        if (account.getId() != null && repository.existsById(account.getId()))
        {
            return null;
        }
        account.setId(System.currentTimeMillis());
        return repository.save(account);
    }

    public void delete(Account account)
    {
        if (account.getId() != null && repository.existsById(account.getId())) {
            repository.delete(account);
        }

    }
    public Optional<Void> update(Long id, Account account) {
        if (account.getId() != null && repository.existsById(account.getId())) {
            Optional<Account> foundAccount = repository.findById(id);
            Account newAccount = foundAccount.get();
            newAccount.setName(account.getName());
            newAccount.setEmail(account.getEmail());
            newAccount.setPassword(account.getPassword());
            newAccount.setAddress(account.getAddress());
            newAccount.setRoles(account.getRoles());
            repository.save(newAccount);
            return Optional.empty();
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Account account = repository.findByName(name);
        if (account == null) {
            throw new UsernameNotFoundException("Invalid user: " + name);
        }
        return new org.springframework.security.core.userdetails.User(account.getName(), account.getPassword(), true,
                true, true,true, AuthorityUtils.commaSeparatedStringToAuthorityList(account.getRoles()));
    }

}
