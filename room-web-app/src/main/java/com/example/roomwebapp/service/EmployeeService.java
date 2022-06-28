package com.example.roomwebapp.service;

import com.example.roomwebapp.dto.AddEmployeeDto;
import com.example.roomwebapp.dto.EmployeeDto;
import com.example.roomwebapp.dto.PartialUpdateEmployeeDto;
import com.example.roomwebapp.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    EmployeeDto createEmployee(AddEmployeeDto addEmployeeDto);
    EmployeeDto updateEmployee(String id, AddEmployeeDto addEmployeeDto);
    EmployeeDto partialEmployeeUpdate(String id, PartialUpdateEmployeeDto partialUpdateEmployeeDto);
    void deleteEmployee(String id);
    EmployeeDto getEmployeeById(String id);

}
