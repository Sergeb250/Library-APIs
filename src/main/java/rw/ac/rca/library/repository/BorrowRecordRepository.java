package rw.ac.rca.library.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rw.ac.rca.library.entity.BorrowRecord;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    
    // Find by borrower ID
    List<BorrowRecord> findByBorrowerId(Long borrowerId);
    Page<BorrowRecord> findByBorrowerId(Long borrowerId, Pageable pageable);
    
    // Find by book ID
    List<BorrowRecord> findByBookId(Long bookId);
    Page<BorrowRecord> findByBookId(Long bookId, Pageable pageable);
    
    // Find by status
    List<BorrowRecord> findByStatus(String status);
    Page<BorrowRecord> findByStatus(String status, Pageable pageable);
    
    // Find by borrower and status
    List<BorrowRecord> findByBorrowerIdAndStatus(Long borrowerId, String status);
    
    // Find overdue records
    @Query("SELECT br FROM BorrowRecord br WHERE br.status = 'BORROWED' AND br.dueDate < :currentDate")
    List<BorrowRecord> findOverdueRecords(LocalDate currentDate);
    
    // Find by province (location-aware)
    @Query("SELECT br FROM BorrowRecord br WHERE br.borrower.village.cell.sector.district.province.name = :provinceName")
    List<BorrowRecord> findByProvince(String provinceName);
    
    // Find by district (location-aware)
    @Query("SELECT br FROM BorrowRecord br WHERE br.borrower.village.cell.sector.district.name = :districtName")
    List<BorrowRecord> findByDistrict(String districtName);
    
    // Find by sector (location-aware)
    @Query("SELECT br FROM BorrowRecord br WHERE br.borrower.village.cell.sector.name = :sectorName")
    List<BorrowRecord> findBySector(String sectorName);
    
    // Find by province and district (location-aware)
    @Query("SELECT br FROM BorrowRecord br WHERE br.borrower.village.cell.sector.district.province.name = :provinceName AND br.borrower.village.cell.sector.district.name = :districtName")
    List<BorrowRecord> findByProvinceAndDistrict(String provinceName, String districtName);
    
    // Find by borrow date range
    @Query("SELECT br FROM BorrowRecord br WHERE br.borrowDate BETWEEN :startDate AND :endDate")
    List<BorrowRecord> findByBorrowDateBetween(LocalDate startDate, LocalDate endDate);
    
    // Count active borrows for a borrower
    @Query("SELECT COUNT(br) FROM BorrowRecord br WHERE br.borrower.id = :borrowerId AND br.status = 'BORROWED'")
    Long countActiveBorrowsByBorrower(Long borrowerId);
    
    // Count active borrows for a book
    @Query("SELECT COUNT(br) FROM BorrowRecord br WHERE br.book.id = :bookId AND br.status = 'BORROWED'")
    Long countActiveBorrowsByBook(Long bookId);
}
