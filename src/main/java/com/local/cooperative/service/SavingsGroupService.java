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

    // ========== Create Savings Group ==========
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

    // ========== Many-to-Many: Add User to Savings Group ==========
    // The Many-to-Many relationship uses a join table "user_savings_group"
    // which has two foreign keys: user_id and savings_group_id.
    // When we add a user to a group, JPA inserts a row into the join table.
    // The @JoinTable annotation on User.savingsGroups defines this mapping.
    public SavingsGroup addMemberToGroup(Long groupId, Long userId) {
        SavingsGroup group = savingsGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Savings Group not found with id: " + groupId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Add the group to user's savingsGroups set (owning side)
        user.getSavingsGroups().add(group);
        userRepository.save(user);

        return savingsGroupRepository.findById(groupId).get();
    }

    // ========== Many-to-Many: Remove User from Savings Group ==========
    // When we remove a user from a group, JPA deletes the row from the join table.
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
