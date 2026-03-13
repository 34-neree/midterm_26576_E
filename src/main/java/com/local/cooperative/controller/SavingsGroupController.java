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

    @PostMapping("/{groupId}/members/{userId}")
    public ResponseEntity<SavingsGroup> addMember(@PathVariable Long groupId, @PathVariable Long userId) {
        SavingsGroup updated = savingsGroupService.addMemberToGroup(groupId, userId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{groupId}/members/{userId}")
    public ResponseEntity<SavingsGroup> removeMember(@PathVariable Long groupId, @PathVariable Long userId) {
        SavingsGroup updated = savingsGroupService.removeMemberFromGroup(groupId, userId);
        return ResponseEntity.ok(updated);
    }
}
