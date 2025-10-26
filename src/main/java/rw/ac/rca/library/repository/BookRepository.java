package rw.ac.rca.library.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rw.ac.rca.library.entity.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    // Find by ISBN
    Optional<Book> findByIsbn(String isbn);
    
    // Check if ISBN exists
    boolean existsByIsbn(String isbn);
    
    // Find by title containing
    List<Book> findByTitleContainingIgnoreCase(String title);
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    
    // Find by author containing
    List<Book> findByAuthorContainingIgnoreCase(String author);
    Page<Book> findByAuthorContainingIgnoreCase(String author, Pageable pageable);
    
    // Find by title or author containing
    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);
    Page<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(
        String title, String author, Pageable pageable
    );
    
    // Find by category
    List<Book> findByCategory(String category);
    Page<Book> findByCategory(String category, Pageable pageable);
    
    // Find available books (availableCopies > 0)
    @Query("SELECT b FROM Book b WHERE b.availableCopies > 0")
    List<Book> findAvailableBooks();
    Page<Book> findByAvailableCopiesGreaterThan(Integer copies, Pageable pageable);
    
    // Find books by publication year
    List<Book> findByPublicationYear(Integer year);
    
    // Find books by publication year range
    @Query("SELECT b FROM Book b WHERE b.publicationYear BETWEEN :startYear AND :endYear")
    List<Book> findByPublicationYearBetween(Integer startYear, Integer endYear);
}
