package org.example.user;

import org.example.account.Account;
import org.example.balance.Balance;
import org.example.balance.BalanceService;
import org.example.basic.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    private final UsersService usersService;
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<Users>>> findAll(){
        return ResponseEntity.ok(ApiResponse.of(usersService.findAll()));
    }

    @GetMapping("/{user_name}")
    public Users getUserByUsername(@PathVariable("user_name") String user_name) {
        System.out.println("in config  users service------------------------------------------------");
        System.out.println("issss: "+usersService.findByUserName(user_name));
        return usersService.findByUserName(user_name);
    }
}
