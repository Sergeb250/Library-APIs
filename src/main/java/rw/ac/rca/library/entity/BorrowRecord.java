package rw.ac.rca.library.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "borrow_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Many-to-One relationship with Book
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    
    // Many-to-One relationship with Borrower
    @ManyToOne
    @JoinColumn(name = "borrower_id", nullable = false)
    private Borrower borrower;
    
    @Column(nullable = false)
    private LocalDate borrowDate;
    
    @Column(nullable = false)
    private LocalDate dueDate;
    
    @Column
    private LocalDate returnDate;
    
    @Column(nullable = false)
    private String status;
    
    @Column
    private String notes;
}
