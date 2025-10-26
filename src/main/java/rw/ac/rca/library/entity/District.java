package rw.ac.rca.library.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "districts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(length = 10)
    private String code;
    
    // Many-to-One: Many Districts belong to one Province
    @ManyToOne
    @JoinColumn(name = "province_id", nullable = false)
    private Province province;
    
    // One-to-Many: One District has many Sectors
    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Sector> sectors = new HashSet<>();
}
