package com.local.cooperative.config;

import com.local.cooperative.model.*;
import com.local.cooperative.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final SectorRepository sectorRepository;
    private final CellRepository cellRepository;
    private final VillageRepository villageRepository;

    public DataLoader(ProvinceRepository provinceRepository,
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

    @Override
    public void run(String... args) {
        // Only seed data if the database is empty
        if (provinceRepository.count() > 0) {
            System.out.println("Database already seeded. Skipping data loading.");
            return;
        }

        System.out.println("Seeding Rwanda location hierarchy...");

        // ========== Province ==========
        Province kigaliCity = provinceRepository.save(new Province("KC", "Kigali City"));

        // ========== Districts under Kigali City ==========
        District gasabo = districtRepository.save(new District("GSB", "Gasabo", kigaliCity));
        District kicukiro = districtRepository.save(new District("KCK", "Kicukiro", kigaliCity));
        District nyarugenge = districtRepository.save(new District("NYR", "Nyarugenge", kigaliCity));

        // ========== Sectors under Gasabo ==========
        Sector remera = sectorRepository.save(new Sector("RMR", "Remera", gasabo));
        Sector kacyiru = sectorRepository.save(new Sector("KCY", "Kacyiru", gasabo));

        // ========== Sectors under Kicukiro ==========
        Sector kagarama = sectorRepository.save(new Sector("KGR", "Kagarama", kicukiro));
        Sector niboye = sectorRepository.save(new Sector("NBY", "Niboye", kicukiro));

        // ========== Sectors under Nyarugenge ==========
        Sector muhima = sectorRepository.save(new Sector("MHM", "Muhima", nyarugenge));
        Sector nyamirambo = sectorRepository.save(new Sector("NYM", "Nyamirambo", nyarugenge));

        // ========== Cells under Remera ==========
        Cell rukiriI = cellRepository.save(new Cell("RK1", "Rukiri I", remera));
        Cell rukiriII = cellRepository.save(new Cell("RK2", "Rukiri II", remera));
        Cell nyabisindu = cellRepository.save(new Cell("NYB", "Nyabisindu", remera));

        // ========== Cells under Kacyiru ==========
        Cell kamatamu = cellRepository.save(new Cell("KMT", "Kamatamu", kacyiru));
        Cell kagugu = cellRepository.save(new Cell("KGG", "Kagugu", kacyiru));

        // ========== Cells under Kagarama ==========
        Cell kanserege = cellRepository.save(new Cell("KNS", "Kanserege", kagarama));
        Cell muyange = cellRepository.save(new Cell("MYG", "Muyange", kagarama));

        // ========== Cells under Niboye ==========
        Cell niboyeCell = cellRepository.save(new Cell("NBC", "Niboye", niboye));
        Cell nyakabanda = cellRepository.save(new Cell("NKB", "Nyakabanda", niboye));

        // ========== Cells under Muhima ==========
        Cell nyabugogo = cellRepository.save(new Cell("NBG", "Nyabugogo", muhima));
        Cell kabasengerezi = cellRepository.save(new Cell("KBS", "Kabasengerezi", muhima));

        // ========== Cells under Nyamirambo ==========
        Cell cyivugiza = cellRepository.save(new Cell("CYV", "Cyivugiza", nyamirambo));
        Cell rugarama = cellRepository.save(new Cell("RGR", "Rugarama", nyamirambo));

        // ========== Villages under Rukiri I ==========
        villageRepository.save(new Village("RK1-VA", "Urukundo", rukiriI));
        villageRepository.save(new Village("RK1-VB", "Ubumwe", rukiriI));
        villageRepository.save(new Village("RK1-VC", "Umucyo", rukiriI));

        // ========== Villages under Rukiri II ==========
        villageRepository.save(new Village("RK2-VA", "Ineza", rukiriII));
        villageRepository.save(new Village("RK2-VB", "Ingabo", rukiriII));

        // ========== Villages under Nyabisindu ==========
        villageRepository.save(new Village("NYB-VA", "Isangano", nyabisindu));
        villageRepository.save(new Village("NYB-VB", "Ikaze", nyabisindu));

        // ========== Villages under Kamatamu ==========
        villageRepository.save(new Village("KMT-VA", "Amahoro", kamatamu));
        villageRepository.save(new Village("KMT-VB", "Intego", kamatamu));

        // ========== Villages under Kagugu ==========
        villageRepository.save(new Village("KGG-VA", "Umuganda", kagugu));
        villageRepository.save(new Village("KGG-VB", "Ubutwari", kagugu));

        // ========== Villages under Kanserege ==========
        villageRepository.save(new Village("KNS-VA", "Ituze", kanserege));
        villageRepository.save(new Village("KNS-VB", "Inkingi", kanserege));

        // ========== Villages under Muyange ==========
        villageRepository.save(new Village("MYG-VA", "Ishema", muyange));
        villageRepository.save(new Village("MYG-VB", "Impano", muyange));

        // ========== Villages under Niboye Cell ==========
        villageRepository.save(new Village("NBC-VA", "Icyizere", niboyeCell));
        villageRepository.save(new Village("NBC-VB", "Umusemburo", niboyeCell));

        // ========== Villages under Nyakabanda ==========
        villageRepository.save(new Village("NKB-VA", "Ubwiyunge", nyakabanda));
        villageRepository.save(new Village("NKB-VB", "Terimbere", nyakabanda));

        // ========== Villages under Nyabugogo ==========
        villageRepository.save(new Village("NBG-VA", "Intwari", nyabugogo));
        villageRepository.save(new Village("NBG-VB", "Urumuri", nyabugogo));

        // ========== Villages under Kabasengerezi ==========
        villageRepository.save(new Village("KBS-VA", "Igihozo", kabasengerezi));
        villageRepository.save(new Village("KBS-VB", "Ibyiwacu", kabasengerezi));

        // ========== Villages under Cyivugiza ==========
        villageRepository.save(new Village("CYV-VA", "Umwezi", cyivugiza));
        villageRepository.save(new Village("CYV-VB", "Izuba", cyivugiza));

        // ========== Villages under Rugarama ==========
        villageRepository.save(new Village("RGR-VA", "Ingenzi", rugarama));
        villageRepository.save(new Village("RGR-VB", "Imena", rugarama));

        System.out.println("✅ Rwanda location hierarchy seeded successfully!");
        System.out.println("   - 1 Province (Kigali City)");
        System.out.println("   - 3 Districts (Gasabo, Kicukiro, Nyarugenge)");
        System.out.println("   - 6 Sectors");
        System.out.println("   - 13 Cells");
        System.out.println("   - 26 Villages");
    }
}
