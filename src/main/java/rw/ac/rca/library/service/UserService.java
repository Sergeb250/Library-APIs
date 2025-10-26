package rw.ac.rca.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.rca.library.dto.UserDTO;
import rw.ac.rca.library.dto.UserDTO;
import rw.ac.rca.library.entity.User;
import rw.ac.rca.library.entity.Village;
import rw.ac.rca.library.repository.UserRepository;
import rw.ac.rca.library.repository.VillageRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository UserRepository;
    private @Autowired VillageRepository villageRepository;
    
    @Autowired
    private LocationService locationService;
    
    // DTO to Entity conversion
    public User toEntity(UserDTO dto) {
        User User = new User();
        User.setId(dto.getId());
        User.setFirstName(dto.getFirstName());
        User.setLastName(dto.getLastName());
        User.setEmail(dto.getEmail());
        User.setPhoneNumber(dto.getPhoneNumber());
        User.setRole(dto.getRole());
        
        if (dto.getVillageId() != null) {
            Village village = villageRepository.findById(dto.getVillageId())
                .orElseThrow(() -> new RuntimeException("Village not found with id: " + dto.getVillageId()));
            User.setVillage(village);
        }
        
        return User;
        
    }
    
    // Entity to DTO conversion
    public UserDTO toDTO(User entity) {
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setRole(entity.getRole());
        
        // Convert nested Location to LocationDTO
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
    
    // Create User
    public UserDTO createUser(UserDTO UserDTO) {
        // Check if email already exists
        if (UserRepository.existsByEmail(UserDTO.getEmail())) {
            throw new RuntimeException("Email already exists: " + UserDTO.getEmail());
        }
        
        User User = toEntity(UserDTO);
        User saved = UserRepository.save(User);
        return toDTO(saved);
    }
    
    // Get all Users
    public List<UserDTO> getAllUsers() {
        return UserRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    // Get all Users with pagination
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return UserRepository.findAll(pageable)
            .map(this::toDTO);
    }
    
    // Get User by ID
    public UserDTO getUserById(Long id) {
        User User = UserRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return toDTO(User);
    }
    
    // Update User
    public UserDTO updateUser(Long id, UserDTO UserDTO) {
        User User = UserRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        // Check if email is being changed and if it already exists
        if (!User.getEmail().equals(UserDTO.getEmail()) && 
            UserRepository.existsByEmail(UserDTO.getEmail())) {
            throw new RuntimeException("Email already exists: " + UserDTO.getEmail());
        }
        
        User.setFirstName(UserDTO.getFirstName());
        User.setLastName(UserDTO.getLastName());
        User.setEmail(UserDTO.getEmail());
        User.setPhoneNumber(UserDTO.getPhoneNumber());
        User.setRole(UserDTO.getRole());
        
       if (UserDTO.getVillageId() != null) {
            Village village = villageRepository.findById(UserDTO.getVillageId())
                .orElseThrow(() -> new RuntimeException("Village not found with id: " + UserDTO.getVillageId()));
            User.setVillage(village);
        }
        
        User updated = UserRepository.save(User);
        return toDTO(updated);
    }
    
    // Delete User
    public void deleteUser(Long id) {
        if (!UserRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        UserRepository.deleteById(id);
    }
    
    // Search users by name
    public List<UserDTO> searchUsersByName(String name) {
        return UserRepository.findByFirstNameContainingOrLastNameContaining(name, name)
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    // Get users by role
    public List<UserDTO> getUsersByRole(String role) {
        return UserRepository.findByRole(role).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    // Get users by province
    public List<UserDTO> getUsersByProvince(String province) {
        return UserRepository.findByProvince(province).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    // Get users by district
    public List<UserDTO> getUsersByDistrict(String district) {
        return UserRepository.findByDistrict(district).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    // Get users by sector
    public List<UserDTO> getUsersBySector(String sector) {
        return UserRepository.findBySector(sector).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    // Get users by province and district
    public List<UserDTO> getUsersByProvinceAndDistrict(String province, String district) {
        return UserRepository.findByProvinceAndDistrict(province, district).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
}
