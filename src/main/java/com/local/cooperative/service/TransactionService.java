package com.local.cooperative.service;

import com.local.cooperative.dto.TransactionRequest;
import com.local.cooperative.enums.TransactionType;
import com.local.cooperative.exception.BadRequestException;
import com.local.cooperative.exception.ResourceNotFoundException;
import com.local.cooperative.model.Account;
import com.local.cooperative.model.Transaction;
import com.local.cooperative.repository.AccountRepository;
import com.local.cooperative.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public Transaction create(TransactionRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        TransactionType type = TransactionType.valueOf(request.getTransactionType().toUpperCase());
        BigDecimal amount = request.getAmount();

        // Update account balance based on transaction type
        switch (type) {
            case DEPOSIT, LOAN_DISBURSEMENT -> account.setBalance(account.getBalance().add(amount));
            case WITHDRAWAL, LOAN_REPAYMENT -> {
                if (account.getBalance().compareTo(amount) < 0) {
                    throw new BadRequestException("Insufficient balance");
                }
                account.setBalance(account.getBalance().subtract(amount));
            }
        }

        accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .transactionType(type)
                .amount(amount)
                .description(request.getDescription())
                .createdAt(LocalDateTime.now())
                .account(account)
                .build();

        return transactionRepository.save(transaction);
    }

    public Page<Transaction> getAll(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return transactionRepository.findAll(pageable);
    }

    public Transaction getById(UUID id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
    }

    public Page<Transaction> getByAccountId(UUID accountId, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return transactionRepository.findByAccountId(accountId, pageable);
    }

    public void delete(UUID id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        transactionRepository.delete(transaction);
    }
}
