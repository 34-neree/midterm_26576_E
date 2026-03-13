package com.local.cooperative.controller;

import com.local.cooperative.model.User;
import com.local.cooperative.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ========== Create User ==========
    // The user only needs to provide: firstName, lastName, email, phone, and villageId
    // The system automatically links the user to the full location hierarchy:
    // Village → Cell → Sector → District → Province
    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody Map<String, Object> request) {
        User user = new User();
        user.setFirstName((String) request.get("firstName"));
        user.setLastName((String) request.get("lastName"));
        user.setEmail((String) request.get("email"));
        user.setPhone((String) request.get("phone"));
        Long villageId = Long.valueOf(request.get("villageId").toString());

        User saved = userService.createUser(user, villageId);

        // Build response with full location hierarchy
        Map<String, Object> response = new HashMap<>();
        response.put("id", saved.getId());
        response.put("firstName", saved.getFirstName());
        response.put("lastName", saved.getLastName());
        response.put("email", saved.getEmail());
        response.put("phone", saved.getPhone());
        response.put("village", saved.getVillage().getName());
        response.put("cell", saved.getVillage().getCell().getName());
        response.put("sector", saved.getVillage().getCell().getSector().getName());
        response.put("district", saved.getVillage().getCell().getSector().getDistrict().getName());
        response.put("province", saved.getVillage().getCell().getSector().getDistrict().getProvince().getName());

        return ResponseEntity.ok(response);
    }

    // ========== Get User by ID ==========
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);

        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("firstName", user.getFirstName());
        response.put("lastName", user.getLastName());
        response.put("email", user.getEmail());
        response.put("phone", user.getPhone());
        response.put("village", user.getVillage().getName());
        response.put("cell", user.getVillage().getCell().getName());
        response.put("sector", user.getVillage().getCell().getSector().getName());
        response.put("district", user.getVillage().getCell().getSector().getDistrict().getName());
        response.put("province", user.getVillage().getCell().getSector().getDistrict().getProvince().getName());

        return ResponseEntity.ok(response);
    }

    // ========== Get All Users with Pagination and Sorting ==========
    // Pagination - splits large datasets into pages for better performance:
    //   page: zero-based page number (default 0)
    //   size: records per page (default 10)
    //
    // Sorting - orders results by a specified field:
    //   sortBy: field name (default "lastName")
    //   direction: "asc" or "desc" (default "asc")
    //
    // Example: GET /api/users?page=0&size=5&sortBy=firstName&direction=asc
    // This returns the first 5 users sorted by firstName in ascending order.
    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "lastName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        Page<User> users = userService.getAllUsers(page, size, sortBy, direction);
        return ResponseEntity.ok(users);
    }

    // ========== Retrieve users by Province Code OR Province Name ==========
    // This uses a JPQL query that traverses the hierarchy:
    // User → Village → Cell → Sector → District → Province
    // Example: GET /api/users/by-province?code=KC
    //      or: GET /api/users/by-province?name=Kigali City
    @GetMapping("/by-province")
    public ResponseEntity<Page<User>> getUsersByProvince(
            @RequestParam(defaultValue = "") String code,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<User> users = userService.getUsersByProvince(code, name, page, size);
        return ResponseEntity.ok(users);
    }

    // ========== existsBy() Method ==========
    // Checks if a user exists by their email address.
    // Spring Data JPA generates: SELECT COUNT(*) > 0 FROM users WHERE email = ?
    // More efficient than loading the full entity - returns only a boolean.
    // Example: GET /api/users/exists?email=john@example.com
    @GetMapping("/exists")
    public ResponseEntity<Map<String, Boolean>> existsByEmail(@RequestParam String email) {
        boolean exists = userService.existsByEmail(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }
}
