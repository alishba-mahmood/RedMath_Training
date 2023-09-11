package org.example.account;

import org.example.basic.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }



    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<Account>>> findAllAccounts()
    {
        System.out.println("\n\n");
        System.out.println("in find all accounts------------------------------------------------");

        return ResponseEntity.ok(ApiResponse.of(service.findAll()));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<List<Object>>>> findAll()
    {
        System.out.println("\n\n");
        System.out.println("in find all accounts/balance/transaction------------------------------------------------");

        return ResponseEntity.ok(ApiResponse.of(service.findAllData()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<Account>>> findById(@PathVariable("id") Long id)
    {
        System.out.println("\n\n");
        System.out.println("in get by id in account------------------------------------------------");

        Optional<Account> accountFound = service.CheckAndDisplay(id);
        if (accountFound.isEmpty())
        {
            return null;
        }
        return ResponseEntity.ok(ApiResponse.of(accountFound));

    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<Account>> create(@RequestBody Account account) {
        System.out.println("\n\n");
        System.out.println("in create accounts------------------------------------------------");

        Account created = service.create(account);
        if (created != null) {
            System.out.println(created);
            return ResponseEntity.ok(ApiResponse.of(created));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('ACCOUNT_HOLDER')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<Account>>> update(@PathVariable Long id, @RequestBody Account account,Authentication auth) {
        System.out.println("\n\n");
        System.out.println("in update accounts------------------------------------------------");

        String login_name = auth.getName();
        Optional<Account> accountFound = service.CheckAndUpdate(id, account,login_name);
        if (accountFound.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.of(accountFound));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAccountById(@PathVariable("id") Long id)
    {
        boolean delete = service.checkAndDelete(id);
        if(delete){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Account>>> getAccountsByNameLike(@RequestParam(name="name") String name)
    {

        System.out.println("in search by name------------------------------------------------");
        List<Account> accounts = service.getAccountsByNameLike(name);
        if (accounts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.of(accounts));
    }

}
