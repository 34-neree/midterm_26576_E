package com.local.cooperative.service;

import com.local.cooperative.model.*;
import com.local.cooperative.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final SectorRepository sectorRepository;
    private final CellRepository cellRepository;
    private final VillageRepository villageRepository;

    public LocationService(ProvinceRepository provinceRepository,
                           DistrictRepository districtRepository,
                           SectorRepository sectorRepository,
                           CellRepository cellRepository,
                           VillageRepository villageRepository) {
        this.provinceRepository = provinceRepository;
        this.districtRepository = districtRepository;
        this.sectorRepository = sectorRepository;
        this.cellRepository = cellRepository;
        this.villageRepository = villageRepository;
    }

    public Province saveProvince(Province province) {
        return provinceRepository.save(province);
    }

    public List<Province> getAllProvinces() {
        return provinceRepository.findAll();
    }

    public Province getProvinceById(Long id) {
        return provinceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Province not found with id: " + id));
    }

    public District saveDistrict(District district, Long provinceId) {
        Province province = provinceRepository.findById(provinceId)
                .orElseThrow(() -> new RuntimeException("Province not found with id: " + provinceId));
        district.setProvince(province);
        return districtRepository.save(district);
    }

    public List<District> getAllDistricts() {
        return districtRepository.findAll();
    }

    public District getDistrictById(Long id) {
        return districtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("District not found with id: " + id));
    }

    public Sector saveSector(Sector sector, Long districtId) {
        District district = districtRepository.findById(districtId)
                .orElseThrow(() -> new RuntimeException("District not found with id: " + districtId));
        sector.setDistrict(district);
        return sectorRepository.save(sector);
    }

    public List<Sector> getAllSectors() {
        return sectorRepository.findAll();
    }

    public Sector getSectorById(Long id) {
        return sectorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sector not found with id: " + id));
    }

    public Cell saveCell(Cell cell, Long sectorId) {
        Sector sector = sectorRepository.findById(sectorId)
                .orElseThrow(() -> new RuntimeException("Sector not found with id: " + sectorId));
        cell.setSector(sector);
        return cellRepository.save(cell);
    }

    public List<Cell> getAllCells() {
        return cellRepository.findAll();
    }

    public Cell getCellById(Long id) {
        return cellRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cell not found with id: " + id));
    }

    public Village saveVillage(Village village, Long cellId) {
        Cell cell = cellRepository.findById(cellId)
                .orElseThrow(() -> new RuntimeException("Cell not found with id: " + cellId));
        village.setCell(cell);
        return villageRepository.save(village);
    }

    public List<Village> getAllVillages() {
        return villageRepository.findAll();
    }

    public Village getVillageById(Long id) {
        return villageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Village not found with id: " + id));
    }

    public boolean villageExistsByCode(String code) {
        return villageRepository.existsByCode(code);
    }
}
