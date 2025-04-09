package com.example.Wedsite_bangiay.repository;

import com.example.Wedsite_bangiay.model.DanhMuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc, Long> {
    // Các phương thức tìm kiếm có thể được thêm vào nếu cần, ví dụ:
    // Optional<DanhMuc> findByName(String name);

}
