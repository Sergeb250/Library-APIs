package rw.ac.rca.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.rca.library.dto.BorrowerDTO;
import rw.ac.rca.library.entity.Borrower;
import rw.ac.rca.library.entity.Village;
import rw.ac.rca.library.repository.BorrowerRepository;
import rw.ac.rca.library.repository.VillageRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BorrowerService {
    
    @Autowired
    private BorrowerRepository borrowerRepository;
    private VillageRepository villageRepository;
    
    @Autowired
    private LocationService locationService;
    
    // DTO to Entity conversion
    public Borrower toEntity(BorrowerDTO dto) {
        Borrower borrower = new Borrower();
        borrower.setId(dto.getId());
        borrower.setFirstName(dto.getFirstName());
        borrower.setLastName(dto.getLastName());
        borrower.setEmail(dto.getEmail());
        borrower.setPhoneNumber(dto.getPhoneNumber());
        borrower.setMembershipNumber(dto.getMembershipNumber());
        
       if (dto.getVillageId() != null) {
            Village village = villageRepository.findById(dto.getVillageId())
                .orElseThrow(() -> new RuntimeException("Village not found with id: " + dto.getVillageId()));
            borrower.setVillage(village);
        }
        
        return borrower;
    }
    
    // Entity to DTO conversion
    public BorrowerDTO toDTO(Borrower entity) {
        BorrowerDTO dto = new BorrowerDTO();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setMembershipNumber(entity.getMembershipNumber());
        
        if (entity.getVillage() != null) {
            dto.setVillageId(entity.getVillage().getId());
            dto.setVillageName(entity.getVillage().getName());
            dto.setCellName(entity.getVillage().getCell().getName());
            dto.setSectorName(entity.getVillage().getCell().getSector().getName());
            dto.setDistrictName(entity.getVillage().getCell().getSector().getDistrict().getName());
            dto.setProvinceName(entity.getVillage().getCell().getSector().getDistrict().getProvince().getName());
            dto.setFullAddress(entity.getVillage().getFullAddress());
        }
        
        
        return dto;
    }
    
    // Create borrower
    public BorrowerDTO createBorrower(BorrowerDTO borrowerDTO) {
        // Check if email already exists
        if (borrowerRepository.existsByEmail(borrowerDTO.getEmail())) {
            throw new RuntimeException("Email already exists: " + borrowerDTO.getEmail());
        }
        
        // Check if membership number already exists
        if (borrowerRepository.existsByMembershipNumber(borrowerDTO.getMembershipNumber())) {
            throw new RuntimeException("Membership number already exists: " + borrowerDTO.getMembershipNumber());
        }
        
        Borrower borrower = toEntity(borrowerDTO);
        Borrower saved = borrowerRepository.save(borrower);
        return toDTO(saved);
    }
    
    // Get all borrowers
    public List<BorrowerDTO> getAllBorrowers() {
        return borrowerRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    // Get all borrowers with pagination
    public Page<BorrowerDTO> getAllBorrowers(Pageable pageable) {
        return borrowerRepository.findAll(pageable)
            .map(this::toDTO);
    }
    
    // Get borrower by ID
    public BorrowerDTO getBorrowerById(Long id) {
        Borrower borrower = borrowerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Borrower not found with id: " + id));
        return toDTO(borrower);
    }
    
    // Update borrower
    public BorrowerDTO updateBorrower(Long id, BorrowerDTO borrowerDTO) {
        Borrower borrower = borrowerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Borrower not found with id: " + id));
        
        // Check if email is being changed and if it already exists
        if (!borrower.getEmail().equals(borrowerDTO.getEmail()) && 
            borrowerRepository.existsByEmail(borrowerDTO.getEmail())) {
            throw new RuntimeException("Email already exists: " + borrowerDTO.getEmail());
        }
        
        // Check if membership number is being changed and if it already exists
        if (!borrower.getMembershipNumber().equals(borrowerDTO.getMembershipNumber()) && 
            borrowerRepository.existsByMembershipNumber(borrowerDTO.getMembershipNumber())) {
            throw new RuntimeException("Membership number already exists: " + borrowerDTO.getMembershipNumber());
        }
        
        borrower.setFirstName(borrowerDTO.getFirstName());
        borrower.setLastName(borrowerDTO.getLastName());
        borrower.setEmail(borrowerDTO.getEmail());
        borrower.setPhoneNumber(borrowerDTO.getPhoneNumber());
        borrower.setMembershipNumber(borrowerDTO.getMembershipNumber());
        
        if (borrowerDTO.getVillageId() != null) {
            Village village = villageRepository.findById(borrowerDTO.getVillageId())
                .orElseThrow(() -> new RuntimeException("Village not found with id: " + borrowerDTO.getVillageId()));
            borrower.setVillage(village);
        }
        
        Borrower updated = borrowerRepository.save(borrower);
        return toDTO(updated);
    }
    
    // Delete borrower
    public void deleteBorrower(Long id) {
        if (!borrowerRepository.existsById(id)) {
            throw new RuntimeException("Borrower not found with id: " + id);
        }
        borrowerRepository.deleteById(id);
    }
    
    // Search borrowers by name
    public List<BorrowerDTO> searchBorrowersByName(String name) {
        return borrowerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name)
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    // Get borrowers by province
    public List<BorrowerDTO> getBorrowersByProvince(String province) {
        return borrowerRepository.findByProvince(province).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    // Get borrowers by district
    public List<BorrowerDTO> getBorrowersByDistrict(String district) {
        return borrowerRepository.findByDistrict(district).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    // Get borrowers by sector
    public List<BorrowerDTO> getBorrowersBySector(String sector) {
        return borrowerRepository.findBySector(sector).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    // Get borrowers by province and district
    public List<BorrowerDTO> getBorrowersByProvinceAndDistrict(String province, String district) {
        return borrowerRepository.findByProvinceAndDistrict(province, district).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
}
