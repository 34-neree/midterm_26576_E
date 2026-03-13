package com.local.cooperative.service;

import com.local.cooperative.dto.AccountRequest;
import com.local.cooperative.enums.AccountType;
import com.local.cooperative.exception.BadRequestException;
import com.local.cooperative.exception.ResourceNotFoundException;
import com.local.cooperative.model.Account;
import com.local.cooperative.model.Member;
import com.local.cooperative.repository.AccountRepository;
import com.local.cooperative.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final MemberRepository memberRepository;

    public Account create(AccountRequest request) {
        if (accountRepository.existsByAccountNumber(request.getAccountNumber())) {
            throw new BadRequestException("Account number already exists");
        }

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

        Account account = Account.builder()
                .accountNumber(request.getAccountNumber())
                .accountType(AccountType.valueOf(request.getAccountType().toUpperCase()))
                .balance(BigDecimal.ZERO)
                .member(member)
                .build();

        return accountRepository.save(account);
    }

    public Page<Account> getAll(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return accountRepository.findAll(pageable);
    }

    public Account getById(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
    }

    public List<Account> getByMemberId(UUID memberId) {
        return accountRepository.findByMemberId(memberId);
    }

    public Account update(UUID id, AccountRequest request) {
        Account account = getById(id);

        if (!account.getAccountNumber().equals(request.getAccountNumber())
                && accountRepository.existsByAccountNumber(request.getAccountNumber())) {
            throw new BadRequestException("Account number already exists");
        }

        account.setAccountNumber(request.getAccountNumber());
        account.setAccountType(AccountType.valueOf(request.getAccountType().toUpperCase()));

        return accountRepository.save(account);
    }

    public void delete(UUID id) {
        Account account = getById(id);
        accountRepository.delete(account);
    }

    public boolean existsByAccountNumber(String accountNumber) {
        return accountRepository.existsByAccountNumber(accountNumber);
    }
}
