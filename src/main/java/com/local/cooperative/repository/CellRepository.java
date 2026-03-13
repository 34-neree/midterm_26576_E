package com.local.cooperative.repository;

import com.local.cooperative.model.Cell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CellRepository extends JpaRepository<Cell, Long> {

    Optional<Cell> findByCode(String code);

    boolean existsByCode(String code);
}
