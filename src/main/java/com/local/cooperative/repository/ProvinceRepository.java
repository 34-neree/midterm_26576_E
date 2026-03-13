package com.local.cooperative.repository;

import com.local.cooperative.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {

    Optional<Province> findByCodeOrName(String code, String name);

    boolean existsByCode(String code);
}
