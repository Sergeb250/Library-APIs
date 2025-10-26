package rw.ac.rca.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRecordSummaryDTO {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private String bookIsbn;
    private Long borrowerId;
    private String borrowerName;
    private String borrowerMembershipNumber;
    private String borrowerProvince;
    private String borrowerDistrict;
    private String borrowerSector;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String status;
}
