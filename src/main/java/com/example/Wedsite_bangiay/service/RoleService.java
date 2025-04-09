package com.example.Wedsite_bangiay.service;

import com.example.Wedsite_bangiay.model.Role;
import com.example.Wedsite_bangiay.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    // Lấy tất cả các vai trò
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Lấy vai trò theo id
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    // Lấy vai trò theo tên
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    // Lưu hoặc cập nhật vai trò
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    // Xóa vai trò
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
