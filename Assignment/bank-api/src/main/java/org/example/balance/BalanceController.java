package org.example.balance;

import org.example.basic.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/balances")
public class BalanceController {
    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public ResponseEntity<ApiResponse<List<Balance>>> findAll(){
        return ResponseEntity.ok(ApiResponse.of(balanceService.findAll()));
    }

    @GetMapping("/account/{id}")public ResponseEntity<ApiResponse<Balance>> findByAccId(@PathVariable("id") Long id)
    {
        System.out.println("\n\n");
        System.out.println("in get  balance by account id------------------------------------------------");

        Balance balance = balanceService.displayBalance(id);
        return ResponseEntity.ok(ApiResponse.of(balance));

    }


}
