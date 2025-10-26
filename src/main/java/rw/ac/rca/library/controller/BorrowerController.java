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
import rw.ac.rca.library.dto.BorrowerDTO;
import rw.ac.rca.library.service.BorrowerService;

import java.util.List;

@RestController
@RequestMapping("/api/borrowers")
public class BorrowerController {
    
    @Autowired
    private BorrowerService borrowerService;
    
    // Create borrower
    @PostMapping
    public ResponseEntity<BorrowerDTO> createBorrower(@Valid @RequestBody BorrowerDTO borrowerDTO) {
        BorrowerDTO created = borrowerService.createBorrower(borrowerDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    // Get all borrowers (with optional pagination)
    @GetMapping
    public ResponseEntity<?> getAllBorrowers(
        @RequestParam(required = false) Boolean paginated,
        @PageableDefault(size = 10, sort = "lastName", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        if (paginated != null && paginated) {
            Page<BorrowerDTO> borrowers = borrowerService.getAllBorrowers(pageable);
            return ResponseEntity.ok(borrowers);
        } else {
            List<BorrowerDTO> borrowers = borrowerService.getAllBorrowers();
            return ResponseEntity.ok(borrowers);
        }
    }
    
    // Get borrower by ID
    @GetMapping("/{id}")
    public ResponseEntity<BorrowerDTO> getBorrowerById(@PathVariable Long id) {
        BorrowerDTO borrower = borrowerService.getBorrowerById(id);
        return ResponseEntity.ok(borrower);
    }
    
    // Update borrower
    @PutMapping("/{id}")
    public ResponseEntity<BorrowerDTO> updateBorrower(
        @PathVariable Long id,
        @Valid @RequestBody BorrowerDTO borrowerDTO
    ) {
        BorrowerDTO updated = borrowerService.updateBorrower(id, borrowerDTO);
        return ResponseEntity.ok(updated);
    }
    
    // Delete borrower
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrower(@PathVariable Long id) {
        borrowerService.deleteBorrower(id);
        return ResponseEntity.noContent().build();
    }
    
    // Search borrowers by name
    @GetMapping("/search")
    public ResponseEntity<List<BorrowerDTO>> searchBorrowersByName(@RequestParam String name) {
        List<BorrowerDTO> borrowers = borrowerService.searchBorrowersByName(name);
        return ResponseEntity.ok(borrowers);
    }
    
    // Get borrowers by province
    @GetMapping("/province/{province}")
    public ResponseEntity<List<BorrowerDTO>> getBorrowersByProvince(@PathVariable String province) {
        List<BorrowerDTO> borrowers = borrowerService.getBorrowersByProvince(province);
        return ResponseEntity.ok(borrowers);
    }
    
    // Get borrowers by district
    @GetMapping("/district/{district}")
    public ResponseEntity<List<BorrowerDTO>> getBorrowersByDistrict(@PathVariable String district) {
        List<BorrowerDTO> borrowers = borrowerService.getBorrowersByDistrict(district);
        return ResponseEntity.ok(borrowers);
    }
    
    // Get borrowers by sector
    @GetMapping("/sector/{sector}")
    public ResponseEntity<List<BorrowerDTO>> getBorrowersBySector(@PathVariable String sector) {
        List<BorrowerDTO> borrowers = borrowerService.getBorrowersBySector(sector);
        return ResponseEntity.ok(borrowers);
    }
    
    // Get borrowers by province and district
    @GetMapping("/province/{province}/district/{district}")
    public ResponseEntity<List<BorrowerDTO>> getBorrowersByProvinceAndDistrict(
        @PathVariable String province,
        @PathVariable String district
    ) {
        List<BorrowerDTO> borrowers = borrowerService.getBorrowersByProvinceAndDistrict(province, district);
        return ResponseEntity.ok(borrowers);
    }
}
