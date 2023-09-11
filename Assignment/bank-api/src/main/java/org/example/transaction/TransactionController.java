package org.example.transaction;

import org.example.basic.ApiResponse;
import org.springframework.http.ResponseEntity;
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

    @GetMapping()
    public ResponseEntity<ApiResponse<List<Transaction>>> findAll(){
        return ResponseEntity.ok(ApiResponse.of(transactionService.findAll()));
    }
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

    @GetMapping("/getId")
    public ApiResponse<Long> getMaxId(){
        System.out.println("getting max id in ctrl\n");
        return ApiResponse.of(transactionService.getMaxId());
    }
    @PostMapping()
    public ResponseEntity<Transaction> create(@RequestBody Transaction transaction) {
        System.out.println("in creation of transaction ------------------------------------------------");
        Transaction created = transactionService.createTransaction(transaction);
        if (created != null) {
            System.out.println("transaction created ------------------------------------------------");

            //balanceService.updateBalanceByAccountId(created.getAccount_id(),created.getAmount(),created.getDB_CR(),created.getDate());
            return ResponseEntity.ok(created);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/account/{id}")
    public ResponseEntity<ApiResponse<List<Transaction>>> findByAccId(@PathVariable("id") Long id)
    {
        System.out.println("\n\n");
        System.out.println("in get  transaction by account id------------------------------------------------");

        List<Transaction> transaction = transactionService.displayTransaction(id);
        return ResponseEntity.ok(ApiResponse.of(transaction));

    }
}
