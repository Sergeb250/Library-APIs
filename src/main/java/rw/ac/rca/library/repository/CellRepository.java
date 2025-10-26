package rw.ac.rca.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.rca.library.entity.Cell;

import java.util.Optional;

@Repository
public interface CellRepository extends JpaRepository<Cell, Long> {
    Optional<Cell> findByName(String name);
}
