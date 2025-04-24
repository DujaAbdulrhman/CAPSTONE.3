package com.example.demo.Repository;

import com.example.demo.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer> {
    Admin findAdminById(Integer id);

    Integer id(Integer id);
}
