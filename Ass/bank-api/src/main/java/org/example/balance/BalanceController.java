package org.example.balance;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/balances")
public class BalanceController {

    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<Balance>> findAll(){
        return ResponseEntity.ok(balanceService.findAll());
    }
    @PostMapping()
    public ResponseEntity<Balance> create(@RequestBody Balance balance) {
        Balance created = balanceService.create(balance);
        if (created != null) {

            balanceService.updateBalanceByAccountId(created.getAccount_id(),created.getAmount(),created.getDB_CR());
            return ResponseEntity.ok(created);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
