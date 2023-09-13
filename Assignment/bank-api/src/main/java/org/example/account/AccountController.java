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

        return ResponseEntity.ok(ApiResponse.of(service.findAll()));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<List<Object>>>> findAll()
    {

        return ResponseEntity.ok(ApiResponse.of(service.findAllData()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<Account>>> findById(@PathVariable("id") Long id, Authentication auth)
    {
        String name = auth.getName();
        Optional<Account> accountFound = service.checkAndDisplay(id,name);
        if (accountFound.isEmpty())
        {
            return null;
        }
        return ResponseEntity.ok(ApiResponse.of(accountFound));

    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<Account>> create(@RequestBody Account account) {


        Account created = service.create(account);
        if (created != null) {
            return ResponseEntity.ok(ApiResponse.of(created));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('ACCOUNT_HOLDER')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<Account>>> update(@PathVariable Long id, @RequestBody Account account,Authentication auth) {

        String login_name = auth.getName();
        Optional<Account> accountFound = service.checkAndUpdate(id, account,login_name);
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
        List<Account> accounts = service.getAccountsByNameLike(name);
        if (accounts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.of(accounts));
    }

}
