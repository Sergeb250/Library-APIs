package rw.ac.rca.library.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String isbn;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String author;
    
    @Column(nullable = false)
    private String publisher;
    
    @Column(nullable = false)
    private Integer publicationYear;
    
    @Column(nullable = false)
    private String category;
    
    @Column(nullable = false)
    private Integer availableCopies;
    
    @Column(nullable = false)
    private Integer totalCopies;
    
    // Many-to-Many relationship with BorrowRecord
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BorrowRecord> borrowRecords = new HashSet<>();
}
