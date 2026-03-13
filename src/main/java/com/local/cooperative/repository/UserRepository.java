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

    // ========== existsBy() method ==========
    // Checks if a user exists by their email address
    // Spring Data JPA translates this into: SELECT COUNT(*) > 0 FROM users WHERE email = ?
    boolean existsByEmail(String email);

    // ========== Retrieve all users from a given province using province code OR province name ==========
    // Uses JPQL query to traverse the hierarchy: User → Village → Cell → Sector → District → Province
    // The :code and :name parameters allow searching by either province code OR province name
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

    // ========== Pagination and Sorting ==========
    // JpaRepository already extends PagingAndSortingRepository
    // So findAll(Pageable pageable) is available by default
    // Pageable contains page number, page size, and Sort direction
}
