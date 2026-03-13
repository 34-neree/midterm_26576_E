package com.local.cooperative.service;

import com.local.cooperative.model.SavingsGroup;
import com.local.cooperative.model.User;
import com.local.cooperative.repository.SavingsGroupRepository;
import com.local.cooperative.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavingsGroupService {

    private final SavingsGroupRepository savingsGroupRepository;
    private final UserRepository userRepository;

    public SavingsGroupService(SavingsGroupRepository savingsGroupRepository,
                                UserRepository userRepository) {
        this.savingsGroupRepository = savingsGroupRepository;
        this.userRepository = userRepository;
    }

    public SavingsGroup createSavingsGroup(SavingsGroup group) {
        return savingsGroupRepository.save(group);
    }

    public SavingsGroup getSavingsGroupById(Long id) {
        return savingsGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Savings Group not found with id: " + id));
    }

    public List<SavingsGroup> getAllSavingsGroups() {
        return savingsGroupRepository.findAll();
    }

    public SavingsGroup addMemberToGroup(Long groupId, Long userId) {
        SavingsGroup group = savingsGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Savings Group not found with id: " + groupId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.getSavingsGroups().add(group);
        userRepository.save(user);

        return savingsGroupRepository.findById(groupId).get();
    }

    public SavingsGroup removeMemberFromGroup(Long groupId, Long userId) {
        SavingsGroup group = savingsGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Savings Group not found with id: " + groupId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.getSavingsGroups().remove(group);
        userRepository.save(user);

        return savingsGroupRepository.findById(groupId).get();
    }
}
