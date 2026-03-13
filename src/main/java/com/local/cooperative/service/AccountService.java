package com.local.cooperative.service;

import com.local.cooperative.model.Account;
import com.local.cooperative.model.User;
import com.local.cooperative.repository.AccountRepository;
import com.local.cooperative.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    // ========== Create Account (One-to-One with User) ==========
    // Each user has exactly one account. The account table has a user_id foreign key
    // with a UNIQUE constraint, ensuring the One-to-One relationship.
    public Account createAccount(Account account, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Check if user already has an account
        if (accountRepository.findByUserId(userId).isPresent()) {
            throw new RuntimeException("User already has an account");
        }

        account.setUser(user);
        return accountRepository.save(account);
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
    }

    public Account getAccountByUserId(Long userId) {
        return accountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Account not found for user id: " + userId));
    }

    public java.util.List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Demonstrates existsBy() method
    public boolean existsByAccountNumber(String accountNumber) {
        return accountRepository.existsByAccountNumber(accountNumber);
    }
}
