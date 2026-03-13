package com.local.cooperative.controller;

import com.local.cooperative.model.*;
import com.local.cooperative.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    // ==================== PROVINCE ====================

    // Save a Province - the top level of the location hierarchy
    @PostMapping("/provinces")
    public ResponseEntity<Province> saveProvince(@RequestBody Province province) {
        Province saved = locationService.saveProvince(province);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/provinces")
    public ResponseEntity<List<Province>> getAllProvinces() {
        return ResponseEntity.ok(locationService.getAllProvinces());
    }

    @GetMapping("/provinces/{id}")
    public ResponseEntity<Province> getProvinceById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getProvinceById(id));
    }

    // ==================== DISTRICT ====================

    // Save a District - linked to a Province via provinceId
    // The relationship is handled by setting the Province object on the District entity
    // JPA stores the province_id foreign key in the districts table
    @PostMapping("/districts")
    public ResponseEntity<District> saveDistrict(@RequestBody Map<String, Object> request) {
        District district = new District();
        district.setCode((String) request.get("code"));
        district.setName((String) request.get("name"));
        Long provinceId = Long.valueOf(request.get("provinceId").toString());
        District saved = locationService.saveDistrict(district, provinceId);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/districts")
    public ResponseEntity<List<District>> getAllDistricts() {
        return ResponseEntity.ok(locationService.getAllDistricts());
    }

    @GetMapping("/districts/{id}")
    public ResponseEntity<District> getDistrictById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getDistrictById(id));
    }

    // ==================== SECTOR ====================

    // Save a Sector - linked to a District via districtId
    @PostMapping("/sectors")
    public ResponseEntity<Sector> saveSector(@RequestBody Map<String, Object> request) {
        Sector sector = new Sector();
        sector.setCode((String) request.get("code"));
        sector.setName((String) request.get("name"));
        Long districtId = Long.valueOf(request.get("districtId").toString());
        Sector saved = locationService.saveSector(sector, districtId);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/sectors")
    public ResponseEntity<List<Sector>> getAllSectors() {
        return ResponseEntity.ok(locationService.getAllSectors());
    }

    @GetMapping("/sectors/{id}")
    public ResponseEntity<Sector> getSectorById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getSectorById(id));
    }

    // ==================== CELL ====================

    // Save a Cell - linked to a Sector via sectorId
    @PostMapping("/cells")
    public ResponseEntity<Cell> saveCell(@RequestBody Map<String, Object> request) {
        Cell cell = new Cell();
        cell.setCode((String) request.get("code"));
        cell.setName((String) request.get("name"));
        Long sectorId = Long.valueOf(request.get("sectorId").toString());
        Cell saved = locationService.saveCell(cell, sectorId);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/cells")
    public ResponseEntity<List<Cell>> getAllCells() {
        return ResponseEntity.ok(locationService.getAllCells());
    }

    @GetMapping("/cells/{id}")
    public ResponseEntity<Cell> getCellById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getCellById(id));
    }

    // ==================== VILLAGE ====================

    // Save a Village - linked to a Cell via cellId
    // This completes saving the hierarchy: Province → District → Sector → Cell → Village
    @PostMapping("/villages")
    public ResponseEntity<Village> saveVillage(@RequestBody Map<String, Object> request) {
        Village village = new Village();
        village.setCode((String) request.get("code"));
        village.setName((String) request.get("name"));
        Long cellId = Long.valueOf(request.get("cellId").toString());
        Village saved = locationService.saveVillage(village, cellId);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/villages")
    public ResponseEntity<List<Village>> getAllVillages() {
        return ResponseEntity.ok(locationService.getAllVillages());
    }

    @GetMapping("/villages/{id}")
    public ResponseEntity<Village> getVillageById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getVillageById(id));
    }

    // Check if a village exists by code - demonstrates existsBy()
    @GetMapping("/villages/exists")
    public ResponseEntity<Map<String, Boolean>> villageExistsByCode(@RequestParam String code) {
        boolean exists = locationService.villageExistsByCode(code);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }
}
