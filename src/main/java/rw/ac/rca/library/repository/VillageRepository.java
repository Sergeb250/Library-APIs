package rw.ac.rca.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.rca.library.entity.Village;

import java.util.Optional;

@Repository
public interface VillageRepository extends JpaRepository<Village, Long> {
    Optional<Village> findByName(String name);
}
