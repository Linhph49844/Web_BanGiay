package com.example.Wedsite_bangiay.repository;

import com.example.Wedsite_bangiay.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Các phương thức tùy chỉnh (nếu cần)
    Role findByName(String name);  // Tìm kiếm vai trò theo tên
}
