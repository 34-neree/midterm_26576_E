package com.local.cooperative.controller;

import com.local.cooperative.dto.AccountRequest;
import com.local.cooperative.model.Account;
import com.local.cooperative.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> create(@RequestBody AccountRequest request) {
        return new ResponseEntity<>(accountService.create(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Account>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "accountNumber") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(accountService.getAll(page, size, sortBy, direction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(accountService.getById(id));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Account>> getByMemberId(@PathVariable UUID memberId) {
        return ResponseEntity.ok(accountService.getByMemberId(memberId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> update(@PathVariable UUID id, @RequestBody AccountRequest request) {
        return ResponseEntity.ok(accountService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/account-number/{accountNumber}")
    public ResponseEntity<Boolean> existsByAccountNumber(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.existsByAccountNumber(accountNumber));
    }
}
