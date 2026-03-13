package com.local.cooperative.repository;

import com.local.cooperative.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    boolean existsByEmail(String email);

    Optional<Member> findByEmailIgnoreCase(String email);

    @Query("""
            SELECT DISTINCT m
            FROM Member m
            JOIN m.location village
            WHERE village.parent.parent.parent.parent.code = :provinceCode
               OR LOWER(village.parent.parent.parent.parent.name) = LOWER(:provinceName)
            """)
    Page<Member> findAllByProvinceCodeOrName(
            @Param("provinceCode") String provinceCode,
            @Param("provinceName") String provinceName,
            Pageable pageable);
}
