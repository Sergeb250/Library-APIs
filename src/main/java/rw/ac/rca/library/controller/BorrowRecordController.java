package rw.ac.rca.library.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.library.dto.BorrowRecordDTO;
import rw.ac.rca.library.dto.BorrowRecordSummaryDTO;
import rw.ac.rca.library.service.BorrowRecordService;

import java.util.List;

@RestController
@RequestMapping("/api/borrowrecords")
public class BorrowRecordController {
    
    @Autowired
    private BorrowRecordService borrowRecordService;
    
    // Create borrow record
    @PostMapping
    public ResponseEntity<BorrowRecordDTO> createBorrowRecord(@Valid @RequestBody BorrowRecordDTO borrowRecordDTO) {
        BorrowRecordDTO created = borrowRecordService.createBorrowRecord(borrowRecordDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    // Get all borrow records (with optional pagination)
    @GetMapping
    public ResponseEntity<?> getAllBorrowRecords(
        @RequestParam(required = false) Boolean paginated,
        @PageableDefault(size = 10, sort = "borrowDate", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        if (paginated != null && paginated) {
            Page<BorrowRecordDTO> records = borrowRecordService.getAllBorrowRecords(pageable);
            return ResponseEntity.ok(records);
        } else {
            List<BorrowRecordDTO> records = borrowRecordService.getAllBorrowRecords();
            return ResponseEntity.ok(records);
        }
    }
    
    // Get all borrow records summary (including location)
    @GetMapping("/summary")
    public ResponseEntity<List<BorrowRecordSummaryDTO>> getAllBorrowRecordsSummary() {
        List<BorrowRecordSummaryDTO> records = borrowRecordService.getAllBorrowRecordsSummary();
        return ResponseEntity.ok(records);
    }
    
    // Get borrow record by ID
    @GetMapping("/{id}")
    public ResponseEntity<BorrowRecordDTO> getBorrowRecordById(@PathVariable Long id) {
        BorrowRecordDTO record = borrowRecordService.getBorrowRecordById(id);
        return ResponseEntity.ok(record);
    }
    
    // Update borrow record
    @PutMapping("/{id}")
    public ResponseEntity<BorrowRecordDTO> updateBorrowRecord(
        @PathVariable Long id,
        @Valid @RequestBody BorrowRecordDTO borrowRecordDTO
    ) {
        BorrowRecordDTO updated = borrowRecordService.updateBorrowRecord(id, borrowRecordDTO);
        return ResponseEntity.ok(updated);
    }
    
    // Return book
    @PutMapping("/{id}/return")
    public ResponseEntity<BorrowRecordDTO> returnBook(@PathVariable Long id) {
        BorrowRecordDTO returned = borrowRecordService.returnBook(id);
        return ResponseEntity.ok(returned);
    }
    
    // Delete borrow record
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrowRecord(@PathVariable Long id) {
        borrowRecordService.deleteBorrowRecord(id);
        return ResponseEntity.noContent().build();
    }
    
    // Get borrow records by borrower
    @GetMapping("/borrower/{borrowerId}")
    public ResponseEntity<List<BorrowRecordDTO>> getBorrowRecordsByBorrower(@PathVariable Long borrowerId) {
        List<BorrowRecordDTO> records = borrowRecordService.getBorrowRecordsByBorrower(borrowerId);
        return ResponseEntity.ok(records);
    }
    
    // Get borrow records by book
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<BorrowRecordDTO>> getBorrowRecordsByBook(@PathVariable Long bookId) {
        List<BorrowRecordDTO> records = borrowRecordService.getBorrowRecordsByBook(bookId);
        return ResponseEntity.ok(records);
    }
    
    // Get borrow records by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<BorrowRecordDTO>> getBorrowRecordsByStatus(@PathVariable String status) {
        List<BorrowRecordDTO> records = borrowRecordService.getBorrowRecordsByStatus(status);
        return ResponseEntity.ok(records);
    }
    
    // Get overdue records
    @GetMapping("/overdue")
    public ResponseEntity<List<BorrowRecordSummaryDTO>> getOverdueRecords() {
        List<BorrowRecordSummaryDTO> records = borrowRecordService.getOverdueRecords();
        return ResponseEntity.ok(records);
    }
    
    // Get borrow records by province
    @GetMapping("/province/{province}")
    public ResponseEntity<List<BorrowRecordSummaryDTO>> getBorrowRecordsByProvince(@PathVariable String province) {
        List<BorrowRecordSummaryDTO> records = borrowRecordService.getBorrowRecordsByProvince(province);
        return ResponseEntity.ok(records);
    }
    
    // Get borrow records by district
    @GetMapping("/district/{district}")
    public ResponseEntity<List<BorrowRecordSummaryDTO>> getBorrowRecordsByDistrict(@PathVariable String district) {
        List<BorrowRecordSummaryDTO> records = borrowRecordService.getBorrowRecordsByDistrict(district);
        return ResponseEntity.ok(records);
    }
    
    // Get borrow records by sector
    @GetMapping("/sector/{sector}")
    public ResponseEntity<List<BorrowRecordSummaryDTO>> getBorrowRecordsBySector(@PathVariable String sector) {
        List<BorrowRecordSummaryDTO> records = borrowRecordService.getBorrowRecordsBySector(sector);
        return ResponseEntity.ok(records);
    }
    
    // Get borrow records by province and district
    @GetMapping("/province/{province}/district/{district}")
    public ResponseEntity<List<BorrowRecordSummaryDTO>> getBorrowRecordsByProvinceAndDistrict(
        @PathVariable String province,
        @PathVariable String district
    ) {
        List<BorrowRecordSummaryDTO> records = borrowRecordService.getBorrowRecordsByProvinceAndDistrict(province, district);
        return ResponseEntity.ok(records);
    }
}
