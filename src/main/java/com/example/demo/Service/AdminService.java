package com.example.demo.Service;

import com.example.demo.API.ApiException;
import com.example.demo.Model.Admin;
import com.example.demo.Repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;


    public Admin getAllAdmin(Integer id){
        return adminRepository.findAdminById(id);
    }

    public void addAdmin(Admin admin){
        adminRepository.save(admin);
    }

    public void updateAdmin(Integer id , Admin admin){
        Admin oldAdmin = adminRepository.findAdminById(id);
        if(oldAdmin == null){
            throw new ApiException("Admin not found");
        }
        oldAdmin.setName(admin.getName());
        oldAdmin.setEmail(admin.getEmail());
        oldAdmin.setPassword(admin.getPassword());

        adminRepository.save(oldAdmin);
    }
}
