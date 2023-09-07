package org.example.transaction;

import org.example.account.Account;
import org.example.balance.BalanceService;
import org.example.basic.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final BalanceService balanceService;

    public TransactionController(TransactionService transactionService, BalanceService balanceService) {
        this.transactionService = transactionService;
        this.balanceService = balanceService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public ResponseEntity<ApiResponse<List<Transaction>>> findAll(){
        return ResponseEntity.ok(ApiResponse.of(transactionService.findAll()));
    }
    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('ACCOUNT_HOLDER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<Transaction>>> findById(@PathVariable("id") Long id, Authentication auth) {
        System.out.println("\n\n");
        System.out.println("in get by id------------------------------------------------");

        String login_name = auth.getName();
        Optional<Transaction> transactionFound = transactionService.CheckAndDisplayTransaction(id, login_name);
        if (transactionFound.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.of(transactionFound));
    }
    @PostMapping()
    public ResponseEntity<Transaction> create(@RequestBody Transaction transaction) {
        Transaction created = transactionService.createTransaction(transaction);
        if (created != null) {

            balanceService.updateBalanceByAccountId(created.getAccount_id(),created.getAmount(),created.getDB_CR());
            return ResponseEntity.ok(created);
        }
        return ResponseEntity.notFound().build();
    }
}
