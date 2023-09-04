package org.example.transaction;

import org.example.balance.BalanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final BalanceService balanceService;

    public TransactionController(TransactionService transactionService, BalanceService balanceService) {
        this.transactionService = transactionService;
        this.balanceService = balanceService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<Transaction>> findAll(){
        return ResponseEntity.ok(transactionService.findAll());
    }
    @PostMapping()
    public ResponseEntity<Transaction> create(@RequestBody Transaction transaction) {
        Transaction created = transactionService.create(transaction);
        if (created != null) {

            balanceService.updateBalanceByAccountId(created.getAccount_id(),created.getAmount(),created.getDB_CR());
            return ResponseEntity.ok(created);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
