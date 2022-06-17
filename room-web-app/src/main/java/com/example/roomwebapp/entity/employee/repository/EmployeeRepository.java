package com.example.roomwebapp.entity.employee.repository;

import com.example.roomwebapp.entity.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,String> {
}
