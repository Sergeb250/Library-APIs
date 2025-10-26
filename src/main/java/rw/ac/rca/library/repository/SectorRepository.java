package rw.ac.rca.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.rca.library.entity.Sector;

import java.util.Optional;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {
    Optional<Sector> findByName(String name);
}
