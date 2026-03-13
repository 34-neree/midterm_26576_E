package com.local.cooperative.repository;

import com.local.cooperative.model.SavingsGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsGroupRepository extends JpaRepository<SavingsGroup, Long> {

    boolean existsByName(String name);
}
