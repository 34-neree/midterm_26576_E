package com.local.cooperative.service;

import com.local.cooperative.dto.SavingsGroupRequest;
import com.local.cooperative.exception.BadRequestException;
import com.local.cooperative.exception.ResourceNotFoundException;
import com.local.cooperative.model.Member;
import com.local.cooperative.model.SavingsGroup;
import com.local.cooperative.repository.MemberRepository;
import com.local.cooperative.repository.SavingsGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SavingsGroupService {

    private final SavingsGroupRepository savingsGroupRepository;
    private final MemberRepository memberRepository;

    public SavingsGroup create(SavingsGroupRequest request) {
        if (savingsGroupRepository.existsByName(request.getName())) {
            throw new BadRequestException("Savings group name already exists");
        }

        SavingsGroup group = SavingsGroup.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        return savingsGroupRepository.save(group);
    }

    public Page<SavingsGroup> getAll(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return savingsGroupRepository.findAll(pageable);
    }

    public SavingsGroup getById(UUID id) {
        return savingsGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Savings group not found"));
    }

    public SavingsGroup update(UUID id, SavingsGroupRequest request) {
        SavingsGroup group = getById(id);
        group.setName(request.getName());
        group.setDescription(request.getDescription());
        return savingsGroupRepository.save(group);
    }

    public void delete(UUID id) {
        SavingsGroup group = getById(id);
        savingsGroupRepository.delete(group);
    }

    public boolean existsByName(String name) {
        return savingsGroupRepository.existsByName(name);
    }

    // Add a member to a savings group (Many-to-Many)
    public SavingsGroup addMember(UUID groupId, UUID memberId) {
        SavingsGroup group = getById(groupId);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

        if (member.getSavingsGroups().contains(group)) {
            throw new BadRequestException("Member is already in this savings group");
        }

        member.getSavingsGroups().add(group);
        memberRepository.save(member);

        return savingsGroupRepository.findById(groupId).orElseThrow();
    }

    // Remove a member from a savings group
    public SavingsGroup removeMember(UUID groupId, UUID memberId) {
        SavingsGroup group = getById(groupId);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

        member.getSavingsGroups().remove(group);
        memberRepository.save(member);

        return savingsGroupRepository.findById(groupId).orElseThrow();
    }
}
