package com.example.roomwebapp.service;


import com.example.roomwebapp.dto.AddEmployeeDto;
import com.example.roomwebapp.dto.EmployeeDto;
import com.example.roomwebapp.dto.PartialUpdateEmployeeDto;
import com.example.roomwebapp.helper.JsonNullableUtils;
import com.example.roomwebapp.repository.EmployeeRepository;
import com.example.roomwebapp.entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private  final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository){
        super();
        this.employeeRepository=employeeRepository;
   }
    public List<Employee> getAllEmployees() {
        return this.employeeRepository.findAll();
    }

    @Override
    public EmployeeDto createEmployee(AddEmployeeDto addEmployeeDto) {
        Employee employee = new Employee();
        employee.setFirstName(addEmployeeDto.getFirstName());
        employee.setLastName(addEmployeeDto.getLastName());
        employee.setPosition(addEmployeeDto.getPosition());

        return employeeRepository.save(employee).toEmployeeDto();
    }

    @Override
    public EmployeeDto updateEmployee(String id, AddEmployeeDto addEmployeeDto) {
        Employee employee=employeeRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "ENTITY NOT FOUND"));
        employee.setFirstName(addEmployeeDto.getFirstName());
        employee.setLastName(addEmployeeDto.getLastName());
        employee.setPosition(addEmployeeDto.getPosition());
        return employeeRepository.save(employee).toEmployeeDto();
    }

    @Override
    public EmployeeDto partialEmployeeUpdate(String id, PartialUpdateEmployeeDto partialUpdateEmployeeDto) {

        Employee employee=employeeRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "ENTITY NOT FOUND"));
        JsonNullableUtils.changeIfPresent(partialUpdateEmployeeDto.getFirstName(),employee::setFirstName);
        JsonNullableUtils.changeIfPresent(partialUpdateEmployeeDto.getLastName(), employee::setLastName);
        JsonNullableUtils.changeIfPresent(partialUpdateEmployeeDto.getPosition(),employee::setPosition);

        return employeeRepository.save(employee).toEmployeeDto();
    }

    @Override
    public void deleteEmployee(String id) {

        Employee employee=employeeRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "ENTITY NOT FOUND"));
        employeeRepository.delete(employee);
    }

    @Override
    public EmployeeDto getEmployeeById(String id) {
        Optional<Employee> result=employeeRepository.findById(id);
        if(result.isPresent()){
            return result.get().toEmployeeDto();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ENTITY NOT FOUND");
        }
    }
}
