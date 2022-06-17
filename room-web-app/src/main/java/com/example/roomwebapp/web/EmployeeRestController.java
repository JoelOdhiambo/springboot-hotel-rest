package com.example.roomwebapp.web;

import com.example.roomwebapp.entity.employee.dto.AddEmployeeDto;
import com.example.roomwebapp.entity.employee.dto.EmployeeDto;
import com.example.roomwebapp.entity.employee.model.Employee;
import com.example.roomwebapp.entity.employee.repository.EmployeeRepository;
import com.example.roomwebapp.entity.employee.service.EmployeeServiceImpl;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

    private final EmployeeServiceImpl employeeService;
    private final EmployeeRepository employeeRepository;

    public EmployeeRestController(EmployeeServiceImpl employeeService, EmployeeRepository employeeRepository) {
        super();
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    private List<EmployeeDto> getAllEmployees(){
        return employeeService.getAllEmployees().stream().map(Employee::toEmployeeDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto>getEmployeeID(@PathVariable(name="id")String id){
        EmployeeDto employeeDto=employeeService.getEmployeeById(id);


        return ResponseEntity.ok().body(employeeDto);
    }

    @PostMapping("/create")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid AddEmployeeDto addEmployeeDto){
        EmployeeDto employeeDto = employeeService.createEmployee(addEmployeeDto);

        return new ResponseEntity<>(employeeDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable String id, @RequestBody AddEmployeeDto addEmployeeDto){
        EmployeeDto employeeDto=employeeService.updateEmployee(id, addEmployeeDto);

        return ResponseEntity.ok().body(employeeDto);
    }

    @PatchMapping("/{id}/name/{firstName},{lastName}")
    public ResponseEntity<Employee> updateName(@PathVariable String id, @PathVariable String firstName,@PathVariable String lastName ){
        Employee employee = employeeRepository.findById(id).get();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        return new ResponseEntity<>(employeeRepository.save(employee),HttpStatus.OK);

    }

    @PatchMapping("/{id}/position/{position}")
    public ResponseEntity<Employee> updatePosition(@PathVariable String id, @PathVariable String position ){
        Employee employee = employeeRepository.findById(id).get();
        employee.setPosition(position);
        return new ResponseEntity<>(employeeRepository.save(employee),HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteEmployee(@PathVariable String id){
        employeeService.deleteEmployee(id);
        ApiResponse apiResponse=new ApiResponse();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
}
