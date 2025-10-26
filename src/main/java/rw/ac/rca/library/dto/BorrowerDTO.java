package rw.ac.rca.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowerDTO {
    private Long id;
    
    @NotBlank(message = "First name is required")
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    private String lastName;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;
    
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    
    @NotBlank(message = "Membership number is required")
    private String membershipNumber;
    
    // Village reference (store the leaf)
    @NotNull(message = "Village is required")
    private Long villageId;
    
    // Read-only fields (populated from hierarchy traversal)
    private String villageName;
    private String cellName;
    private String sectorName;
    private String districtName;
    private String provinceName;
    private String fullAddress;
}
