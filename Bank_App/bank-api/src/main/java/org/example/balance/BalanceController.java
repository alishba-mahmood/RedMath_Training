package org.example.balance;

import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/api/v1/balances")
public class BalanceController {
/*
    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<Balance>> findAll(){
        return ResponseEntity.ok(balanceService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Balance>> findById(@PathVariable("id") Long id)
    {
        Optional<Balance> balance = balanceService.findById(id);

        if(balance == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(balance);
    }

    @PostMapping()
    public ResponseEntity<Balance> create(@RequestBody Balance balance) {
        Balance created = balanceService.create(balance);
        if (created != null) {

            balanceService.updateBalanceByAccountId(created.getAccount_id(),created.getAmount(),created.getDB_CR());
            return ResponseEntity.ok(created);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }*/
}
