package com.local.cooperative.repository;

import com.local.cooperative.model.SavingsGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SavingsGroupRepository extends JpaRepository<SavingsGroup, UUID> {
    boolean existsByName(String name);
}
