package rw.ac.rca.library.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rw.ac.rca.library.entity.Borrower;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
    
    // Find by email
    Optional<Borrower> findByEmail(String email);
    
    // Find by membership number
    Optional<Borrower> findByMembershipNumber(String membershipNumber);
    
    // Check if email exists
    boolean existsByEmail(String email);
    
    // Check if membership number exists
    boolean existsByMembershipNumber(String membershipNumber);
    
    // Find by name containing
    List<Borrower> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
        String firstName, String lastName
    );
    Page<Borrower> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
        String firstName, String lastName, Pageable pageable
    );
    
    // Find by province
    @Query("SELECT b FROM Borrower b WHERE b.village.cell.sector.district.province.name = :provinceName")
    List<Borrower> findByProvince(String provinceName);
    
    // Find by district
    @Query("SELECT b FROM Borrower b WHERE b.village.cell.sector.district.name = :districtName")
    List<Borrower> findByDistrict(String districtName);
    
    // Find by sector
    @Query("SELECT b FROM Borrower b WHERE b.village.cell.sector.name = :sectorName")
    List<Borrower> findBySector(String sectorName);
    
    // Find by province and district
    @Query("SELECT b FROM Borrower b WHERE b.village.cell.sector.district.province.name = :provinceName AND b.village.cell.sector.district.name = :districtName")
    List<Borrower> findByProvinceAndDistrict(String provinceName, String districtName);
}
