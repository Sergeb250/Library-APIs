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
import rw.ac.rca.library.dto.LocationDTO;
import rw.ac.rca.library.service.LocationService;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    
    @Autowired
    private LocationService locationService;
    
    // Create location
    @PostMapping
    public ResponseEntity<LocationDTO> createLocation(@Valid @RequestBody LocationDTO locationDTO) {
        LocationDTO created = locationService.createLocation(locationDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    // Get all locations (with optional pagination)
    @GetMapping
    public ResponseEntity<?> getAllLocations(
        @RequestParam(required = false) Boolean paginated,
        @PageableDefault(size = 10, sort = "province", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        if (paginated != null && paginated) {
            Page<LocationDTO> locations = locationService.getAllLocations(pageable);
            return ResponseEntity.ok(locations);
        } else {
            List<LocationDTO> locations = locationService.getAllLocations();
            return ResponseEntity.ok(locations);
        }
    }
    
    // Get location by ID
    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> getLocationById(@PathVariable Long id) {
        LocationDTO location = locationService.getLocationById(id);
        return ResponseEntity.ok(location);
    }
    
    // Update location
    @PutMapping("/{id}")
    public ResponseEntity<LocationDTO> updateLocation(
        @PathVariable Long id,
        @Valid @RequestBody LocationDTO locationDTO
    ) {
        LocationDTO updated = locationService.updateLocation(id, locationDTO);
        return ResponseEntity.ok(updated);
    }
    
    // Delete location
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
    
    // Get locations by province
    @GetMapping("/province/{province}")
    public ResponseEntity<List<LocationDTO>> getLocationsByProvince(@PathVariable String province) {
        List<LocationDTO> locations = locationService.getLocationsByProvince(province);
        return ResponseEntity.ok(locations);
    }
    
    // Get locations by district
    @GetMapping("/district/{district}")
    public ResponseEntity<List<LocationDTO>> getLocationsByDistrict(@PathVariable String district) {
        List<LocationDTO> locations = locationService.getLocationsByDistrict(district);
        return ResponseEntity.ok(locations);
    }
    
    // Get locations by province and district
    @GetMapping("/province/{province}/district/{district}")
    public ResponseEntity<List<LocationDTO>> getLocationsByProvinceAndDistrict(
        @PathVariable String province,
        @PathVariable String district
    ) {
        List<LocationDTO> locations = locationService.getLocationsByProvinceAndDistrict(province, district);
        return ResponseEntity.ok(locations);
    }
    
    // Get hierarchy: distinct provinces
    @GetMapping("/hierarchy/provinces")
    public ResponseEntity<List<String>> getProvinces() {
        List<String> provinces = locationService.getDistinctProvinces();
        return ResponseEntity.ok(provinces);
    }
    
    // Get hierarchy: districts by province
    @GetMapping("/hierarchy/provinces/{province}/districts")
    public ResponseEntity<List<String>> getDistrictsByProvince(@PathVariable String province) {
        List<String> districts = locationService.getDistrictsByProvince(province);
        return ResponseEntity.ok(districts);
    }
    
    // Get hierarchy: sectors by district
    @GetMapping("/hierarchy/districts/{district}/sectors")
    public ResponseEntity<List<String>> getSectorsByDistrict(@PathVariable String district) {
        List<String> sectors = locationService.getSectorsByDistrict(district);
        return ResponseEntity.ok(sectors);
    }
}
