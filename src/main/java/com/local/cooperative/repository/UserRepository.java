package com.local.cooperative.repository;

import com.local.cooperative.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u " +
           "JOIN u.village v " +
           "JOIN v.cell c " +
           "JOIN c.sector s " +
           "JOIN s.district d " +
           "JOIN d.province p " +
           "WHERE p.code = :code OR p.name = :name")
    Page<User> findAllByProvinceCodeOrProvinceName(
            @Param("code") String code,
            @Param("name") String name,
            Pageable pageable);
}
