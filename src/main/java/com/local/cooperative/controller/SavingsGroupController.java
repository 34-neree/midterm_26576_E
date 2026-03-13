package com.local.cooperative.controller;

import com.local.cooperative.model.SavingsGroup;
import com.local.cooperative.service.SavingsGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/savings-groups")
public class SavingsGroupController {

    private final SavingsGroupService savingsGroupService;

    public SavingsGroupController(SavingsGroupService savingsGroupService) {
        this.savingsGroupService = savingsGroupService;
    }

    // ========== Create Savings Group ==========
    @PostMapping
    public ResponseEntity<SavingsGroup> createSavingsGroup(@RequestBody SavingsGroup group) {
        SavingsGroup saved = savingsGroupService.createSavingsGroup(group);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SavingsGroup> getSavingsGroupById(@PathVariable Long id) {
        return ResponseEntity.ok(savingsGroupService.getSavingsGroupById(id));
    }

    @GetMapping
    public ResponseEntity<List<SavingsGroup>> getAllSavingsGroups() {
        return ResponseEntity.ok(savingsGroupService.getAllSavingsGroups());
    }

    // ========== Many-to-Many: Add User to Savings Group ==========
    // The Many-to-Many relationship between User and SavingsGroup uses
    // a join table called "user_savings_group" with two columns:
    //   - user_id (FK to users table)
    //   - savings_group_id (FK to savings_groups table)
    //
    // When a user is added to a group, JPA inserts a new row into
    // the join table: INSERT INTO user_savings_group (user_id, savings_group_id) VALUES (?, ?)
    @PostMapping("/{groupId}/members/{userId}")
    public ResponseEntity<SavingsGroup> addMember(@PathVariable Long groupId, @PathVariable Long userId) {
        SavingsGroup updated = savingsGroupService.addMemberToGroup(groupId, userId);
        return ResponseEntity.ok(updated);
    }

    // ========== Many-to-Many: Remove User from Savings Group ==========
    // Removes the row from the join table:
    // DELETE FROM user_savings_group WHERE user_id = ? AND savings_group_id = ?
    @DeleteMapping("/{groupId}/members/{userId}")
    public ResponseEntity<SavingsGroup> removeMember(@PathVariable Long groupId, @PathVariable Long userId) {
        SavingsGroup updated = savingsGroupService.removeMemberFromGroup(groupId, userId);
        return ResponseEntity.ok(updated);
    }
}
