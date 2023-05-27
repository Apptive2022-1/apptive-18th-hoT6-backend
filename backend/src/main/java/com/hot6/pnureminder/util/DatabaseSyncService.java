package com.hot6.pnureminder.util;

import com.hot6.pnureminder.entity.Department;
import com.hot6.pnureminder.repository.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DatabaseSyncService {

    private final MongoTemplate mongoTemplate;
    private final DepartmentRepository departmentRepository;

    public DatabaseSyncService(MongoTemplate mongoTemplate, DepartmentRepository departmentRepository) {
        this.mongoTemplate = mongoTemplate;
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public void syncDepartments() {
        // Get all collection names from MongoDB
        Set<String> mongoCollections = new HashSet<>(mongoTemplate.getCollectionNames());

        // Get all department names from MySQL
        Set<String> mysqlDepartments = departmentRepository.findAll().stream()
                .map(Department::getName)
                .collect(Collectors.toSet());

        // Find out which collections are not yet in MySQL
        mongoCollections.removeAll(mysqlDepartments);

        // Add missing departments to MySQL
        for (String departmentName : mongoCollections) {
            Department department = new Department();
            department.setName(departmentName);
            departmentRepository.save(department);
        }
    }
}
