package rw.ac.rca.library.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "locations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String province;
    
    @Column(nullable = false)
    private String district;
    
    @Column(nullable = false)
    private String sector;
    
    @Column(nullable = false)
    private String cell;
    
    @Column(nullable = false)
    private String village;
}
