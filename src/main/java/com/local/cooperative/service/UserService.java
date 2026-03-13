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

    public User createUser(User user, Long villageId) {
        Village village = villageRepository.findById(villageId)
                .orElseThrow(() -> new RuntimeException("Village not found with id: " + villageId));
        user.setVillage(village);
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public Page<User> getAllUsers(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return userRepository.findAll(pageable);
    }

    public Page<User> getUsersByProvince(String code, String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAllByProvinceCodeOrProvinceName(code, name, pageable);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
