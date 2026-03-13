package com.local.cooperative.controller;

import com.local.cooperative.model.Account;
import com.local.cooperative.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Map<String, Object> request) {
        Account account = new Account();
        account.setAccountNumber((String) request.get("accountNumber"));
        account.setBalance(Double.valueOf(request.get("balance").toString()));
        account.setAccountType((String) request.get("accountType"));
        Long userId = Long.valueOf(request.get("userId").toString());

        Account saved = accountService.createAccount(account, userId);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Account> getAccountByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.getAccountByUserId(userId));
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/exists")
    public ResponseEntity<Map<String, Boolean>> existsByAccountNumber(@RequestParam String accountNumber) {
        boolean exists = accountService.existsByAccountNumber(accountNumber);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }
}
