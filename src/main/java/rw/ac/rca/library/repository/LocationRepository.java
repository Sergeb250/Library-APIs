package rw.ac.rca.library.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rw.ac.rca.library.entity.Location;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    
    // Find by province
    List<Location> findByProvince(String province);
    Page<Location> findByProvince(String province, Pageable pageable);
    
    // Find by district
    List<Location> findByDistrict(String district);
    Page<Location> findByDistrict(String district, Pageable pageable);
    
    // Find by province and district
    List<Location> findByProvinceAndDistrict(String province, String district);
    
    // Find by sector
    List<Location> findBySector(String sector);
    
    // Find by cell
    List<Location> findByCell(String cell);
    
    // Find by village
    List<Location> findByVillage(String village);
    
    // Check existence
    boolean existsByProvinceAndDistrictAndSectorAndCellAndVillage(
        String province, String district, String sector, String cell, String village
    );
    
    // Get distinct provinces
    @Query("SELECT DISTINCT l.province FROM Location l ORDER BY l.province")
    List<String> findDistinctProvinces();
    
    // Get districts by province
    @Query("SELECT DISTINCT l.district FROM Location l WHERE l.province = :province ORDER BY l.district")
    List<String> findDistinctDistrictsByProvince(String province);
    
    // Get sectors by district
    @Query("SELECT DISTINCT l.sector FROM Location l WHERE l.district = :district ORDER BY l.sector")
    List<String> findDistinctSectorsByDistrict(String district);
}
