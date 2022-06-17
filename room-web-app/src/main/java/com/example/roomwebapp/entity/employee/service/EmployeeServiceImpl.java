package com.example.roomwebapp.entity.employee.service;


import com.example.roomwebapp.entity.employee.dto.AddEmployeeDto;
import com.example.roomwebapp.entity.employee.dto.EmployeeDto;
import com.example.roomwebapp.entity.employee.repository.EmployeeRepository;
import com.example.roomwebapp.entity.employee.model.Employee;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

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
                .orElseThrow(()->new NotFoundException("Employee" + id));
        employee.setFirstName(addEmployeeDto.getFirstName());
        employee.setLastName(addEmployeeDto.getLastName());
        employee.setPosition(addEmployeeDto.getPosition());
        return employeeRepository.save(employee).toEmployeeDto();
    }



    @Override
    public Employee updateName(String id, Employee employeeRequest) {
        Employee employee=employeeRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Employee" + id));
        employee.setFirstName(employeeRequest.getFirstName());


        return employeeRepository.save(employee);
    }

    @Override
    public Employee updatePosition(String id, Employee employeeRequest) {
        Employee employee=employeeRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Employee" + id));
        employee.setPosition(employeeRequest.getPosition());

        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(String id) {
        Employee employee=employeeRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Employee " + id));
        employeeRepository.delete(employee);
    }

    @Override
    public EmployeeDto getEmployeeById(String id) {
        Optional<Employee> result=employeeRepository.findById(id);
        if(result.isPresent()){
            return result.get().toEmployeeDto();
        }else{
            throw new NotFoundException("Employee "+ id);
        }
    }
}
