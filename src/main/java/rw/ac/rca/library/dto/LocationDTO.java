package rw.ac.rca.library.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {
    private Long id;
    
    @NotBlank(message = "Province is required")
    private String province;
    
    @NotBlank(message = "District is required")
    private String district;
    
    @NotBlank(message = "Sector is required")
    private String sector;
    
    @NotBlank(message = "Cell is required")
    private String cell;
    
    @NotBlank(message = "Village is required")
    private String village;
}
