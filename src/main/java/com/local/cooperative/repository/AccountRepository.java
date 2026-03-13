package com.local.cooperative.repository;

import com.local.cooperative.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    boolean existsByAccountNumber(String accountNumber);

    List<Account> findByMemberId(UUID memberId);
}
