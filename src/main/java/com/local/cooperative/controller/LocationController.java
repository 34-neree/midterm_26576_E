package com.local.cooperative.controller;

import com.local.cooperative.dto.LocationRequest;
import com.local.cooperative.enums.LocationType;
import com.local.cooperative.model.Location;
import com.local.cooperative.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<Location> create(@RequestBody LocationRequest request) {
        Location location = Location.builder()
                .code(request.getCode())
                .name(request.getName())
                .type(LocationType.valueOf(request.getType().toUpperCase()))
                .build();

        Location saved = locationService.create(location, request.getParentId());
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Location>> getAll() {
        return ResponseEntity.ok(locationService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(locationService.getById(id));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Location>> getByType(@PathVariable String type) {
        return ResponseEntity.ok(locationService.getByType(LocationType.valueOf(type.toUpperCase())));
    }

    @GetMapping("/provinces")
    public ResponseEntity<List<Location>> getProvinces() {
        return ResponseEntity.ok(locationService.getProvinces());
    }

    @GetMapping("/parent/{parentId}")
    public ResponseEntity<List<Location>> getByParentId(@PathVariable UUID parentId) {
        return ResponseEntity.ok(locationService.getByParentId(parentId));
    }

    @GetMapping("/exists/{code}")
    public ResponseEntity<Boolean> existsByCode(@PathVariable String code) {
        return ResponseEntity.ok(locationService.existsByCode(code));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        locationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
