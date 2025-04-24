
package com.example.demo.Controller;


import com.example.demo.API.ApiResponse;
import com.example.demo.Model.Admin;
import com.example.demo.Service.AdminService;
import com.example.demo.Service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;
    private final PropertyService propertyService;


    @GetMapping("/get/{id}")
    public ResponseEntity getAdmin(@PathVariable Integer id){
        return ResponseEntity.status(200).body(adminService.getAllAdmin(id));
    }


    @PostMapping("/add")
    public ResponseEntity addAdmin(@Valid @RequestBody Admin admin){
        adminService.addAdmin(admin);
        return ResponseEntity.status(200).body(new ApiResponse("Admin added successfully"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity updateAdmin(@PathVariable Integer id, @Valid @RequestBody Admin admin){
        adminService.updateAdmin(id, admin);
        return ResponseEntity.status(200).body(new ApiResponse("Admin updated successfully"));

    }

    @PutMapping("/activate/{propertyId}/{adminId}")
    public ResponseEntity activateTheProperty(@PathVariable Integer propertyId,@PathVariable Integer adminId){
        propertyService.activeTheProperty(propertyId, adminId);
        return ResponseEntity.status(200).body(new ApiResponse("Property is now active and visible to investors"));
    }

    @PutMapping("/rejectProperty/{propertyId}/{adminId}")
    public ResponseEntity rejectTheProperty(@PathVariable Integer propertyId, @PathVariable Integer adminId) {
        propertyService.rejectTheProperty(propertyId, adminId);
        return ResponseEntity.status(200).body(new ApiResponse("Property has been rejected and hidden from investors"));

    }

    @GetMapping("/get-property{adminId}")
    public ResponseEntity getProperty(@PathVariable Integer adminId){
        return ResponseEntity.status(200).body(propertyService.getAllPropertyByAdmin(adminId));
    }


}


