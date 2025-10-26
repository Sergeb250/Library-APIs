package rw.ac.rca.library.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cells")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(length = 10)
    private String code;
    
    // Many-to-One: Many Cells belong to one Sector
    @ManyToOne
    @JoinColumn(name = "sector_id", nullable = false)
    private Sector sector;
    
    // One-to-Many: One Cell has many Villages
    @OneToMany(mappedBy = "cell", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Village> villages = new HashSet<>();
}
