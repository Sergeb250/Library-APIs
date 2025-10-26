package rw.ac.rca.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.rca.library.dto.BorrowRecordDTO;
import rw.ac.rca.library.dto.BorrowRecordSummaryDTO;
import rw.ac.rca.library.entity.Book;
import rw.ac.rca.library.entity.BorrowRecord;
import rw.ac.rca.library.entity.Borrower;
import rw.ac.rca.library.repository.BookRepository;
import rw.ac.rca.library.repository.BorrowRecordRepository;
import rw.ac.rca.library.repository.BorrowerRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BorrowRecordService {
    
    @Autowired
    private BorrowRecordRepository borrowRecordRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private BorrowerRepository borrowerRepository;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private BorrowerService borrowerService;
    
    // DTO to Entity conversion
    public BorrowRecord toEntity(BorrowRecordDTO dto) {
        BorrowRecord record = new BorrowRecord();
        record.setId(dto.getId());
        
        // Convert nested DTOs to entities
        if (dto.getBook() != null && dto.getBook().getId() != null) {
            Book book = bookRepository.findById(dto.getBook().getId())
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + dto.getBook().getId()));
            record.setBook(book);
        }
        
        if (dto.getBorrower() != null && dto.getBorrower().getId() != null) {
            Borrower borrower = borrowerRepository.findById(dto.getBorrower().getId())
                .orElseThrow(() -> new RuntimeException("Borrower not found with id: " + dto.getBorrower().getId()));
            record.setBorrower(borrower);
        }
        
        record.setBorrowDate(dto.getBorrowDate());
        record.setDueDate(dto.getDueDate());
        record.setReturnDate(dto.getReturnDate());
        record.setStatus(dto.getStatus());
        record.setNotes(dto.getNotes());
        
        return record;
    }
    
    // Entity to DTO conversion (full)
    public BorrowRecordDTO toDTO(BorrowRecord entity) {
        BorrowRecordDTO dto = new BorrowRecordDTO();
        dto.setId(entity.getId());
        
        // Convert nested entities to DTOs
        if (entity.getBook() != null) {
            dto.setBook(bookService.toDTO(entity.getBook()));
        }
        
        if (entity.getBorrower() != null) {
            dto.setBorrower(borrowerService.toDTO(entity.getBorrower()));
        }
        
        dto.setBorrowDate(entity.getBorrowDate());
        dto.setDueDate(entity.getDueDate());
        dto.setReturnDate(entity.getReturnDate());
        dto.setStatus(entity.getStatus());
        dto.setNotes(entity.getNotes());
        
        return dto;
    }
    
    // Entity to Summary DTO conversion (simplified with location)
    public BorrowRecordSummaryDTO toSummaryDTO(BorrowRecord entity) {
        BorrowRecordSummaryDTO dto = new BorrowRecordSummaryDTO();
        dto.setId(entity.getId());
        
        // Book details
        if (entity.getBook() != null) {
            dto.setBookId(entity.getBook().getId());
            dto.setBookTitle(entity.getBook().getTitle());
            dto.setBookIsbn(entity.getBook().getIsbn());
        }
        
        // Borrower details with location (hierarchical)
        if (entity.getBorrower() != null) {
            dto.setBorrowerId(entity.getBorrower().getId());
            dto.setBorrowerName(entity.getBorrower().getFirstName() + " " + entity.getBorrower().getLastName());
            dto.setBorrowerMembershipNumber(entity.getBorrower().getMembershipNumber());
            
            if (entity.getBorrower().getVillage() != null) {
                dto.setBorrowerProvince(entity.getBorrower().getVillage().getProvince().getName());
                dto.setBorrowerDistrict(entity.getBorrower().getVillage().getDistrict().getName());
                dto.setBorrowerSector(entity.getBorrower().getVillage().getSector().getName());
            }
        }
        
        dto.setBorrowDate(entity.getBorrowDate());
        dto.setDueDate(entity.getDueDate());
        dto.setReturnDate(entity.getReturnDate());
        dto.setStatus(entity.getStatus());
        
        return dto;
    }
    
    // Create borrow record
    public BorrowRecordDTO createBorrowRecord(BorrowRecordDTO borrowRecordDTO) {
        // Validate book availability
        Book book = bookRepository.findById(borrowRecordDTO.getBook().getId())
            .orElseThrow(() -> new RuntimeException("Book not found"));
        
        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("Book is not available for borrowing");
        }
        
        // Create borrow record
        BorrowRecord record = toEntity(borrowRecordDTO);
        BorrowRecord saved = borrowRecordRepository.save(record);
        
        // Decrease available copies
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);
        
        return toDTO(saved);
    }
    
    // Get all borrow records
    public List<BorrowRecordDTO> getAllBorrowRecords() {
        return borrowRecordRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    // Get all borrow records with pagination
    public Page<BorrowRecordDTO> getAllBorrowRecords(Pageable pageable) {
        return borrowRecordRepository.findAll(pageable)
            .map(this::toDTO);
    }
    
    // Get all borrow records as summary
    public List<BorrowRecordSummaryDTO> getAllBorrowRecordsSummary() {
        return borrowRecordRepository.findAll().stream()
            .map(this::toSummaryDTO)
            .collect(Collectors.toList());
    }
    
    // Get borrow record by ID
    public BorrowRecordDTO getBorrowRecordById(Long id) {
        BorrowRecord record = borrowRecordRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Borrow record not found with id: " + id));
        return toDTO(record);
    }
    
    // Update borrow record
    public BorrowRecordDTO updateBorrowRecord(Long id, BorrowRecordDTO borrowRecordDTO) {
        BorrowRecord record = borrowRecordRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Borrow record not found with id: " + id));
        
        record.setBorrowDate(borrowRecordDTO.getBorrowDate());
        record.setDueDate(borrowRecordDTO.getDueDate());
        record.setReturnDate(borrowRecordDTO.getReturnDate());
        record.setStatus(borrowRecordDTO.getStatus());
        record.setNotes(borrowRecordDTO.getNotes());
        
        BorrowRecord updated = borrowRecordRepository.save(record);
        return toDTO(updated);
    }
    
    // Return book
    public BorrowRecordDTO returnBook(Long id) {
        BorrowRecord record = borrowRecordRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Borrow record not found with id: " + id));
        
        if (!"BORROWED".equals(record.getStatus())) {
            throw new RuntimeException("Book has already been returned");
        }
        
        // Update record
        record.setReturnDate(LocalDate.now());
        record.setStatus("RETURNED");
        BorrowRecord updated = borrowRecordRepository.save(record);
        
        // Increase available copies
        Book book = record.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);
        
        return toDTO(updated);
    }
    
    // Delete borrow record
    public void deleteBorrowRecord(Long id) {
        if (!borrowRecordRepository.existsById(id)) {
            throw new RuntimeException("Borrow record not found with id: " + id);
        }
        borrowRecordRepository.deleteById(id);
    }
    
    // Get borrow records by borrower
    public List<BorrowRecordDTO> getBorrowRecordsByBorrower(Long borrowerId) {
        return borrowRecordRepository.findByBorrowerId(borrowerId).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    // Get borrow records by book
    public List<BorrowRecordDTO> getBorrowRecordsByBook(Long bookId) {
        return borrowRecordRepository.findByBookId(bookId).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    // Get borrow records by status
    public List<BorrowRecordDTO> getBorrowRecordsByStatus(String status) {
        return borrowRecordRepository.findByStatus(status).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    // Get overdue records
    public List<BorrowRecordSummaryDTO> getOverdueRecords() {
        return borrowRecordRepository.findOverdueRecords(LocalDate.now()).stream()
            .map(this::toSummaryDTO)
            .collect(Collectors.toList());
    }
    
    // Get borrow records by province
    public List<BorrowRecordSummaryDTO> getBorrowRecordsByProvince(String province) {
        return borrowRecordRepository.findByProvince(province).stream()
            .map(this::toSummaryDTO)
            .collect(Collectors.toList());
    }
    
    // Get borrow records by district
    public List<BorrowRecordSummaryDTO> getBorrowRecordsByDistrict(String district) {
        return borrowRecordRepository.findByDistrict(district).stream()
            .map(this::toSummaryDTO)
            .collect(Collectors.toList());
    }
    
    // Get borrow records by sector
    public List<BorrowRecordSummaryDTO> getBorrowRecordsBySector(String sector) {
        return borrowRecordRepository.findBySector(sector).stream()
            .map(this::toSummaryDTO)
            .collect(Collectors.toList());
    }
    
    // Get borrow records by province and district
    public List<BorrowRecordSummaryDTO> getBorrowRecordsByProvinceAndDistrict(String province, String district) {
        return borrowRecordRepository.findByProvinceAndDistrict(province, district).stream()
            .map(this::toSummaryDTO)
            .collect(Collectors.toList());
    }
}
