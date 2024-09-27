package com.example.exodia.department.repository;

import com.example.exodia.department.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findDepartmentById(Long departmentId);
}
