package rw.ac.rca.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.rca.library.dto.BookDTO;
import rw.ac.rca.library.entity.Book;
import rw.ac.rca.library.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;
    
    // DTO to Entity conversion
    public Book toEntity(BookDTO dto) {
        Book book = new Book();
        book.setId(dto.getId());
        book.setIsbn(dto.getIsbn());
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPublisher(dto.getPublisher());
        book.setPublicationYear(dto.getPublicationYear());
        book.setCategory(dto.getCategory());
        book.setAvailableCopies(dto.getAvailableCopies());
        book.setTotalCopies(dto.getTotalCopies());
        return book;
    }
    
    // Entity to DTO conversion
    public BookDTO toDTO(Book entity) {
        BookDTO dto = new BookDTO();
        dto.setId(entity.getId());
        dto.setIsbn(entity.getIsbn());
        dto.setTitle(entity.getTitle());
        dto.setAuthor(entity.getAuthor());
        dto.setPublisher(entity.getPublisher());
        dto.setPublicationYear(entity.getPublicationYear());
        dto.setCategory(entity.getCategory());
        dto.setAvailableCopies(entity.getAvailableCopies());
        dto.setTotalCopies(entity.getTotalCopies());
        return dto;
    }
    
    // Create book
    public BookDTO createBook(BookDTO bookDTO) {
        // Check if ISBN already exists
        if (bookRepository.existsByIsbn(bookDTO.getIsbn())) {
            throw new RuntimeException("Book with ISBN already exists: " + bookDTO.getIsbn());
        }
        
        // Validate available copies doesn't exceed total copies
        if (bookDTO.getAvailableCopies() > bookDTO.getTotalCopies()) {
            throw new RuntimeException("Available copies cannot exceed total copies");
        }
        
        Book book = toEntity(bookDTO);
        Book saved = bookRepository.save(book);
        return toDTO(saved);
    }
    
    // Get all books
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    // Get all books with pagination
    public Page<BookDTO> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
            .map(this::toDTO);
    }
    
    // Get book by ID
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        return toDTO(book);
    }
    
    // Update book
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        
        // Check if ISBN is being changed and if it already exists
        if (!book.getIsbn().equals(bookDTO.getIsbn()) && 
            bookRepository.existsByIsbn(bookDTO.getIsbn())) {
            throw new RuntimeException("Book with ISBN already exists: " + bookDTO.getIsbn());
        }
        
        // Validate available copies doesn't exceed total copies
        if (bookDTO.getAvailableCopies() > bookDTO.getTotalCopies()) {
            throw new RuntimeException("Available copies cannot exceed total copies");
        }
        
        book.setIsbn(bookDTO.getIsbn());
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPublisher(bookDTO.getPublisher());
        book.setPublicationYear(bookDTO.getPublicationYear());
        book.setCategory(bookDTO.getCategory());
        book.setAvailableCopies(bookDTO.getAvailableCopies());
        book.setTotalCopies(bookDTO.getTotalCopies());
        
        Book updated = bookRepository.save(book);
        return toDTO(updated);
    }
    
    // Delete book
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }
    
    // Search books by title
    public List<BookDTO> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    // Search books by author
    public List<BookDTO> searchBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    // Search books by title or author
    public List<BookDTO> searchBooks(String query) {
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query)
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    // Get books by category
    public List<BookDTO> getBooksByCategory(String category) {
        return bookRepository.findByCategory(category).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    // Get available books
    public List<BookDTO> getAvailableBooks() {
        return bookRepository.findAvailableBooks().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    // Get books by publication year
    public List<BookDTO> getBooksByPublicationYear(Integer year) {
        return bookRepository.findByPublicationYear(year).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
}
