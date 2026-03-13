package com.local.cooperative.repository;

import com.local.cooperative.model.Village;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VillageRepository extends JpaRepository<Village, Long> {

    Optional<Village> findByNameOrCode(String name, String code);

    boolean existsByCode(String code);

    boolean existsByName(String name);
}
