package rw.ac.rca.library.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "villages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Village {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(length = 10)
    private String code;
    
    // Many-to-One: Many Villages belong to one Cell
    @ManyToOne
    @JoinColumn(name = "cell_id", nullable = false)
    private Cell cell;
    
    // Convenience method to get full hierarchy
    public String getFullAddress() {
        return String.format("%s, %s, %s, %s, %s",
            cell.getSector().getDistrict().getProvince().getName(),
            cell.getSector().getDistrict().getName(),
            cell.getSector().getName(),
            cell.getName(),
            name
        );
    }
    
    // Get Province from Village (traversing up)
    public Province getProvince() {
        return cell.getSector().getDistrict().getProvince();
    }
    
    // Get District from Village (traversing up)
    public District getDistrict() {
        return cell.getSector().getDistrict();
    }
    
    // Get Sector from Village (traversing up)
    public Sector getSector() {
        return cell.getSector();
    }
}
