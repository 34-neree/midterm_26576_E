package com.local.cooperative.service;

import com.local.cooperative.model.User;
import com.local.cooperative.model.Village;
import com.local.cooperative.repository.UserRepository;
import com.local.cooperative.repository.VillageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final VillageRepository villageRepository;

    public UserService(UserRepository userRepository, VillageRepository villageRepository) {
        this.userRepository = userRepository;
        this.villageRepository = villageRepository;
    }

    // ========== Create User ==========
    // When creating a user, only the villageId is required.
    // The system automatically resolves: Village → Cell → Sector → District → Province
    // because of the JPA relationships defined in the entity classes.
    public User createUser(User user, Long villageId) {
        Village village = villageRepository.findById(villageId)
                .orElseThrow(() -> new RuntimeException("Village not found with id: " + villageId));
        user.setVillage(village);
        return userRepository.save(user);
    }

    // ========== Get User by ID ==========
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // ========== Pagination and Sorting ==========
    // Pagination: divides large datasets into smaller pages for better performance
    //   - page: zero-based page index (0 = first page)
    //   - size: number of records per page
    //
    // Sorting: orders results by specified field(s) and direction (ASC/DESC)
    //   - sortBy: field name to sort by (e.g., "lastName", "firstName")
    //   - direction: "asc" or "desc"
    //
    // Spring Data JPA's Pageable combines both into a single database query
    // with LIMIT and OFFSET clauses, improving performance by only loading
    // the requested subset of data instead of all records.
    public Page<User> getAllUsers(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return userRepository.findAll(pageable);
    }

    // ========== Retrieve users by Province Code OR Province Name ==========
    // This query traverses the entire location hierarchy:
    // User → Village → Cell → Sector → District → Province
    // Users can search by either province code (e.g., "KC") or name (e.g., "Kigali City")
    public Page<User> getUsersByProvince(String code, String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAllByProvinceCodeOrProvinceName(code, name, pageable);
    }

    // ========== existsBy() method ==========
    // Spring Data JPA generates: SELECT COUNT(*) > 0 FROM users WHERE email = ?
    // Returns true if at least one record matches, false otherwise.
    // More efficient than findByEmail() because it doesn't load the full entity.
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
