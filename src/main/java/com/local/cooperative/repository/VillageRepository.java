package com.local.cooperative.repository;

import com.local.cooperative.model.Village;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VillageRepository extends JpaRepository<Village, Long> {

    // Find a village by name or code
    Optional<Village> findByNameOrCode(String name, String code);

    // Demonstrates existsBy() method - checks if a village exists by its code
    boolean existsByCode(String code);

    // Demonstrates existsBy() method - checks if a village exists by its name
    boolean existsByName(String name);
}
