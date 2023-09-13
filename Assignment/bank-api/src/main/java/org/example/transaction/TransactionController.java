package org.example.transaction;

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
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public ResponseEntity<ApiResponse<List<Transaction>>> findAll(){
        return ResponseEntity.ok(ApiResponse.of(transactionService.findAll()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<Transaction>>> findById(@PathVariable("id") Long id, Authentication auth) {

        String login_name = auth.getName();
        Optional<Transaction> transactionFound = transactionService.checkAndDisplayTransaction(id, login_name);
        if (transactionFound.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.of(transactionFound));
    }

    @GetMapping("/getId")
    public ApiResponse<Long> getMaxId(){
        return ApiResponse.of(transactionService.getMaxId());
    }
    
    @PostMapping()
    public ResponseEntity<Transaction> create(@RequestBody Transaction transaction) {
        Transaction created = transactionService.createTransaction(transaction);
        if (created != null) {
            return ResponseEntity.ok(created);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/account/{id}")
    public ResponseEntity<ApiResponse<Optional<List<Transaction>>>> findByAccId(@PathVariable("id") Long id,Authentication auth)
    {
        String name = auth.getName();
        Optional<List<Transaction>> transactions = transactionService.displayTransactionsOfAccount(id,name);
        return ResponseEntity.ok(ApiResponse.of(transactions));
    }




}
