package com.example.roomwebapp.entity.employee.service;

import com.example.roomwebapp.entity.employee.dto.AddEmployeeDto;
import com.example.roomwebapp.entity.employee.dto.EmployeeDto;
import com.example.roomwebapp.entity.employee.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    EmployeeDto createEmployee(AddEmployeeDto addEmployeeDto);
    EmployeeDto updateEmployee(String id, AddEmployeeDto addEmployeeDto);
    void deleteEmployee(String id);
    EmployeeDto getEmployeeById(String id);

}
