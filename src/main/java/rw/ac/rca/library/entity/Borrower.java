package rw.ac.rca.library.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "borrowers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Borrower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String phoneNumber;
    
    @Column(unique = true, nullable = false)
    private String membershipNumber;
    
    // Many-to-One relationship with Village (leaf node in hierarchy)
    @ManyToOne
    @JoinColumn(name = "village_id", nullable = false)
    private Village village;
    
    // One-to-Many relationship with BorrowRecord
    @OneToMany(mappedBy = "borrower", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BorrowRecord> borrowRecords = new HashSet<>();
}
