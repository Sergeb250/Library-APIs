package rw.ac.rca.library.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sectors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(length = 10)
    private String code;
    
    // Many-to-One: Many Sectors belong to one District
    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private District district;
    
    // One-to-Many: One Sector has many Cells
    @OneToMany(mappedBy = "sector", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cell> cells = new HashSet<>();
}
