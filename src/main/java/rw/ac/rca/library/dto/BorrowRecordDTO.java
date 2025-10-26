package rw.ac.rca.library.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRecordDTO {
    private Long id;
    
    @NotNull(message = "Book is required")
    private BookDTO book;
    
    @NotNull(message = "Borrower is required")
    private BorrowerDTO borrower;
    
    @NotNull(message = "Borrow date is required")
    private LocalDate borrowDate;
    
    @NotNull(message = "Due date is required")
    private LocalDate dueDate;
    
    private LocalDate returnDate;
    
    @NotNull(message = "Status is required")
    private String status;
    
    private String notes;
}
