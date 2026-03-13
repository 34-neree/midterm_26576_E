package com.local.cooperative.repository;

import com.local.cooperative.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    // Demonstrates existsBy() method
    boolean existsByAccountNumber(String accountNumber);

    // Find account by user id
    Optional<Account> findByUserId(Long userId);
}
