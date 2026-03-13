package com.local.cooperative.repository;

import com.local.cooperative.enums.LocationType;
import com.local.cooperative.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {
    Optional<Location> findByCode(String code);

    boolean existsByCode(String code);

    List<Location> findByType(LocationType type);

    List<Location> findByParentId(UUID parentId);

    @Query("SELECT l FROM Location l WHERE l.type = 'PROVINCE'")
    List<Location> findAllProvinces();
}
