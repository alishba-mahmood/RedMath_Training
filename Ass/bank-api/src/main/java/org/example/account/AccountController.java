package org.example.account;

import org.example.balance.Balance;
import org.example.balance.BalanceService;
import org.example.basic.ApiResponse;
import org.example.transaction.Transaction;
import org.example.transaction.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/api")
public class AccountController {

    private final AccountService service;

    private final BalanceService balanceService;

    private final TransactionService transactionService;
    public AccountController(AccountService service, BalanceService balanceService, TransactionService transactionService) {
        this.service = service;
        this.balanceService = balanceService;
        this.transactionService = transactionService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<List<Object>>>> findAll()
    {
        List<List<Object>> nestedLists = new ArrayList<>();
        nestedLists.add(new ArrayList<>(service.findAll()));
        nestedLists.add(new ArrayList<>(transactionService.findAll()));
        nestedLists.add(new ArrayList<>(balanceService.findAll()));

        return ResponseEntity.ok(ApiResponse.of(nestedLists));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/accounts")
    public ResponseEntity<ApiResponse<List<Account>>> findAllAccounts()
    {
        return ResponseEntity.ok(ApiResponse.of(service.findAll()));
    }

    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('ACCOUNT_HOLDER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<List<Object>>> findById(@PathVariable("id") Long id, Authentication auth)
    {
        String login_name = auth.getName();
        if( login_name.equals("admin"))
        {
            List<Object> data = new ArrayList<>();
            Optional<Account> acc = service.findById(id);
            if(acc.isEmpty())
            {
                return ResponseEntity.notFound().build();
            }
            data.add(acc);
            Balance bal = balanceService.findByAccountId(id);
            if(bal == null)
            {
                return ResponseEntity.ok(ApiResponse.of(data));
            }
            data.add(bal);
            Transaction tran = transactionService.findByBalanceId(bal.getId());
            if(tran == null)
            {
                return ResponseEntity.ok(ApiResponse.of(data));
            }
            data.add(tran);
            return ResponseEntity.ok(ApiResponse.of(data));
        }
        Optional<Long> login_id = service.findIdByName(login_name);
        if( login_id == null)
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Long matched_id = login_id.get();
        if(id == matched_id)
        {
            List<Object> data = new ArrayList<>();
            Optional<Account> acc = service.findById(id);
            if(acc.isEmpty())
            {
                return ResponseEntity.notFound().build();
            }
            data.add(acc);
            Balance bal = balanceService.findByAccountId(id);
            if(bal == null)
            {
                return ResponseEntity.ok(ApiResponse.of(data));
            }
            data.add(bal);
            Transaction tran = transactionService.findByBalanceId(bal.getId());
            if(tran == null)
            {
                return ResponseEntity.ok(ApiResponse.of(data));
            }
            data.add(tran);
            return ResponseEntity.ok(ApiResponse.of(data));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();


    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping()
    public ResponseEntity<ApiResponse<Account>> create(@RequestBody Account account) {
        Account created = service.create(account);
        if (created != null) {
            return ResponseEntity.ok(ApiResponse.of(created));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable Long id, @RequestBody Account account) {
        Optional<Account> accountFound = service.findById(id);
        if (accountFound.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.update(id,account);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {

        Optional<Account> acc = service.findById(id);
        if(acc.isEmpty())
        {
            return ResponseEntity.notFound().build();
        }

        Account delAccount = acc.get();
        Balance bal = balanceService.findByAccountId(id);
        if(bal == null)
        {
            service.delete(delAccount);
            return ResponseEntity.noContent().build();
        }
        Transaction tran = transactionService.findByAccountId(id);
        if(tran == null)
        {
            balanceService.deleteByAccountId(id);
            service.delete(delAccount);
            return ResponseEntity.noContent().build();
        }
        transactionService.deleteByAccountId(id);
        balanceService.deleteByAccountId(id);
        service.delete(delAccount);
        return ResponseEntity.noContent().build();
    }
}
