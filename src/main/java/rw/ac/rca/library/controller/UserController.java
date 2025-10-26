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
import rw.ac.rca.library.dto.UserDTO;
import rw.ac.rca.library.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    // Create user
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO created = userService.createUser(userDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    // Get all users (with optional pagination)
    @GetMapping
    public ResponseEntity<?> getAllUsers(
        @RequestParam(required = false) Boolean paginated,
        @PageableDefault(size = 10, sort = "lastName", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        if (paginated != null && paginated) {
            Page<UserDTO> users = userService.getAllUsers(pageable);
            return ResponseEntity.ok(users);
        } else {
            List<UserDTO> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        }
    }
    
    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    
    // Update user
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
        @PathVariable Long id,
        @Valid @RequestBody UserDTO userDTO
    ) {
        UserDTO updated = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updated);
    }
    
    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    // Search users by name
    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchUsersByName(@RequestParam String name) {
        List<UserDTO> users = userService.searchUsersByName(name);
        return ResponseEntity.ok(users);
    }
    
    // Get users by role
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserDTO>> getUsersByRole(@PathVariable String role) {
        List<UserDTO> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }
    
    // Get users by province
    @GetMapping("/province/{province}")
    public ResponseEntity<List<UserDTO>> getUsersByProvince(@PathVariable String province) {
        List<UserDTO> users = userService.getUsersByProvince(province);
        return ResponseEntity.ok(users);
    }
    
    // Get users by district
    @GetMapping("/district/{district}")
    public ResponseEntity<List<UserDTO>> getUsersByDistrict(@PathVariable String district) {
        List<UserDTO> users = userService.getUsersByDistrict(district);
        return ResponseEntity.ok(users);
    }
    
    // Get users by province and district
    @GetMapping("/province/{province}/district/{district}")
    public ResponseEntity<List<UserDTO>> getUsersByProvinceAndDistrict(
        @PathVariable String province,
        @PathVariable String district
    ) {
        List<UserDTO> users = userService.getUsersByProvinceAndDistrict(province, district);
        return ResponseEntity.ok(users);
    }
}
