package com.carp.carpentry.repository;

import com.carp.carpentry.entity.Department;
import com.carp.carpentry.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
    Department findByDepName(String dep_name);
}
