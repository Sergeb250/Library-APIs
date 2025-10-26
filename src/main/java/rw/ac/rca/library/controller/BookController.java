package rw.ac.rca.library.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.library.dto.BookDTO;
import rw.ac.rca.library.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    // Create book
    @PostMapping
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) {
        BookDTO created = bookService.createBook(bookDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    // Get all books (with optional pagination)
    @GetMapping
    public ResponseEntity<?> getAllBooks(
        @RequestParam(required = false) Boolean paginated,
        @PageableDefault(size = 10, sort = "title", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        if (paginated != null && paginated) {
            Page<BookDTO> books = bookService.getAllBooks(pageable);
            return ResponseEntity.ok(books);
        } else {
            List<BookDTO> books = bookService.getAllBooks();
            return ResponseEntity.ok(books);
        }
    }
    
    // Get book by ID
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }
    
    // Update book
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(
        @PathVariable Long id,
        @Valid @RequestBody BookDTO bookDTO
    ) {
        BookDTO updated = bookService.updateBook(id, bookDTO);
        return ResponseEntity.ok(updated);
    }
    
    // Delete book
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
    
    // Search books by query (title or author)
    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> searchBooks(@RequestParam String query) {
        List<BookDTO> books = bookService.searchBooks(query);
        return ResponseEntity.ok(books);
    }
    
    // Search books by title
    @GetMapping("/search/title")
    public ResponseEntity<List<BookDTO>> searchBooksByTitle(@RequestParam String title) {
        List<BookDTO> books = bookService.searchBooksByTitle(title);
        return ResponseEntity.ok(books);
    }
    
    // Search books by author
    @GetMapping("/search/author")
    public ResponseEntity<List<BookDTO>> searchBooksByAuthor(@RequestParam String author) {
        List<BookDTO> books = bookService.searchBooksByAuthor(author);
        return ResponseEntity.ok(books);
    }
    
    // Get books by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<BookDTO>> getBooksByCategory(@PathVariable String category) {
        List<BookDTO> books = bookService.getBooksByCategory(category);
        return ResponseEntity.ok(books);
    }
    
    // Get available books
    @GetMapping("/available")
    public ResponseEntity<List<BookDTO>> getAvailableBooks() {
        List<BookDTO> books = bookService.getAvailableBooks();
        return ResponseEntity.ok(books);
    }
    
    // Get books by publication year
    @GetMapping("/year/{year}")
    public ResponseEntity<List<BookDTO>> getBooksByPublicationYear(@PathVariable Integer year) {
        List<BookDTO> books = bookService.getBooksByPublicationYear(year);
        return ResponseEntity.ok(books);
    }
}
