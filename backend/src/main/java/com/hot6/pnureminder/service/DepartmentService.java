package com.hot6.pnureminder.service;

import com.hot6.pnureminder.entity.Department;
import com.hot6.pnureminder.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public Optional<Department> getDepartmentByName(String departmentName) {
        return departmentRepository.findByName(departmentName);
    }

}
