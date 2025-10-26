package rw.ac.rca.library.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rw.ac.rca.library.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Find by email
    Optional<User> findByEmail(String email);
    
    // Check if email exists
    boolean existsByEmail(String email);
    
    // Find by first name or last name containing
    List<User> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
    Page<User> findByFirstNameContainingOrLastNameContaining(
        String firstName, String lastName, Pageable pageable
    );
    
    // Find by role
    List<User> findByRole(String role);
    Page<User> findByRole(String role, Pageable pageable);
    
    // Find by province
    @Query("SELECT u FROM User u WHERE u.village.cell.sector.district.province.name = :provinceName")
    List<User> findByProvince(String provinceName);
    
    // Find by district
    @Query("SELECT u FROM User u WHERE u.village.cell.sector.district.name = :districtName")
    List<User> findByDistrict(String districtName);
    
    // Find by sector
    @Query("SELECT u FROM User u WHERE u.village.cell.sector.name = :sectorName")
    List<User> findBySector(String sectorName);
    
    // Find by province and district
    @Query("SELECT u FROM User u WHERE u.village.cell.sector.district.province.name = :provinceName AND u.village.cell.sector.district.name = :districtName")
    List<User> findByProvinceAndDistrict(String provinceName, String districtName);
}
