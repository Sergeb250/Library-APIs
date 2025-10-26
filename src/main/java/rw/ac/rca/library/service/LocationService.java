package rw.ac.rca.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.rca.library.dto.LocationDTO;
import rw.ac.rca.library.entity.Location;
import rw.ac.rca.library.entity.Village;
import rw.ac.rca.library.repository.LocationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LocationService {
    
    @Autowired
    private LocationRepository locationRepository;
    
    // DTO to Entity conversion
    public Location toEntity(LocationDTO dto) {
        Location location = new Location();
        location.setId(dto.getId());
        location.setProvince(dto.getProvince());
        location.setDistrict(dto.getDistrict());
        location.setSector(dto.getSector());
        location.setCell(dto.getCell());
        location.setVillage(dto.getVillage());
        return location;
    }
    
    // Entity to DTO conversion
    public LocationDTO toDTO(Location entity) {
        LocationDTO dto = new LocationDTO();
        dto.setId(entity.getId());
        dto.setProvince(entity.getProvince());
        dto.setDistrict(entity.getDistrict());
        dto.setSector(entity.getSector());
        dto.setCell(entity.getCell());
        dto.setVillage(entity.getVillage());
        return dto;
    }
    
    // Create location
    public LocationDTO createLocation(LocationDTO locationDTO) {
        // Check if location already exists
        boolean exists = locationRepository.existsByProvinceAndDistrictAndSectorAndCellAndVillage(
            locationDTO.getProvince(),
            locationDTO.getDistrict(),
            locationDTO.getSector(),
            locationDTO.getCell(),
            locationDTO.getVillage()
        );
        
        if (exists) {
            throw new RuntimeException("Location already exists");
        }
        
        Location location = toEntity(locationDTO);
        Location saved = locationRepository.save(location);
        return toDTO(saved);
    }
    
    // Get all locations
    public List<LocationDTO> getAllLocations() {
        return locationRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    // Get all locations with pagination
    public Page<LocationDTO> getAllLocations(Pageable pageable) {
        return locationRepository.findAll(pageable)
            .map(this::toDTO);
    }
    
    // Get location by ID
    public LocationDTO getLocationById(Long id) {
        Location location = locationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Location not found with id: " + id));
        return toDTO(location);
    }
    
    // Update location
    public LocationDTO updateLocation(Long id, LocationDTO locationDTO) {
        Location location = locationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Location not found with id: " + id));
        
        location.setProvince(locationDTO.getProvince());
        location.setDistrict(locationDTO.getDistrict());
        location.setSector(locationDTO.getSector());
        location.setCell(locationDTO.getCell());
        location.setVillage(locationDTO.getVillage());
        
        Location updated = locationRepository.save(location);
        return toDTO(updated);
    }
    
    // Delete location
    public void deleteLocation(Long id) {
        if (!locationRepository.existsById(id)) {
            throw new RuntimeException("Location not found with id: " + id);
        }
        locationRepository.deleteById(id);
    }
    
    // Get locations by province
    public List<LocationDTO> getLocationsByProvince(String province) {
        return locationRepository.findByProvince(province).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    // Get locations by district
    public List<LocationDTO> getLocationsByDistrict(String district) {
        return locationRepository.findByDistrict(district).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    // Get locations by province and district
    public List<LocationDTO> getLocationsByProvinceAndDistrict(String province, String district) {
        return locationRepository.findByProvinceAndDistrict(province, district).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    // Get distinct provinces
    public List<String> getDistinctProvinces() {
        return locationRepository.findDistinctProvinces();
    }
    
    // Get districts by province
    public List<String> getDistrictsByProvince(String province) {
        return locationRepository.findDistinctDistrictsByProvince(province);
    }
    
    // Get sectors by district
    public List<String> getSectorsByDistrict(String district) {
        return locationRepository.findDistinctSectorsByDistrict(district);
    }

    

   
}