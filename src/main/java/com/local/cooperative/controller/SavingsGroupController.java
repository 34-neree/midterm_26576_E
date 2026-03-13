package com.local.cooperative.controller;

import com.local.cooperative.dto.SavingsGroupRequest;
import com.local.cooperative.model.SavingsGroup;
import com.local.cooperative.service.SavingsGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/savings-groups")
@RequiredArgsConstructor
public class SavingsGroupController {

    private final SavingsGroupService savingsGroupService;

    @PostMapping
    public ResponseEntity<SavingsGroup> create(@RequestBody SavingsGroupRequest request) {
        return new ResponseEntity<>(savingsGroupService.create(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<SavingsGroup>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(savingsGroupService.getAll(page, size, sortBy, direction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SavingsGroup> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(savingsGroupService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SavingsGroup> update(@PathVariable UUID id, @RequestBody SavingsGroupRequest request) {
        return ResponseEntity.ok(savingsGroupService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        savingsGroupService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/name/{name}")
    public ResponseEntity<Boolean> existsByName(@PathVariable String name) {
        return ResponseEntity.ok(savingsGroupService.existsByName(name));
    }

    // Many-to-Many: add member to savings group
    @PostMapping("/{groupId}/members/{memberId}")
    public ResponseEntity<SavingsGroup> addMember(@PathVariable UUID groupId, @PathVariable UUID memberId) {
        return ResponseEntity.ok(savingsGroupService.addMember(groupId, memberId));
    }

    // Many-to-Many: remove member from savings group
    @DeleteMapping("/{groupId}/members/{memberId}")
    public ResponseEntity<SavingsGroup> removeMember(@PathVariable UUID groupId, @PathVariable UUID memberId) {
        return ResponseEntity.ok(savingsGroupService.removeMember(groupId, memberId));
    }
}
