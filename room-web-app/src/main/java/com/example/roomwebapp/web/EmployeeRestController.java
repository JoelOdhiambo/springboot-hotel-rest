package com.example.roomwebapp.web;

import com.example.roomwebapp.dto.AddEmployeeDto;
import com.example.roomwebapp.dto.EmployeeDto;
import com.example.roomwebapp.dto.PartialUpdateEmployeeDto;
import com.example.roomwebapp.entity.Employee;
import com.example.roomwebapp.repository.EmployeeRepository;
import com.example.roomwebapp.service.EmployeeServiceImpl;
import com.example.roomwebapp.helper.JsonNullableUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
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


        return new ResponseEntity<>(employeeDto,HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid AddEmployeeDto addEmployeeDto){
        EmployeeDto employeeDto = employeeService.createEmployee(addEmployeeDto);

        return new ResponseEntity<>(employeeDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable String id, @RequestBody AddEmployeeDto addEmployeeDto){
        EmployeeDto employeeDto=employeeService.updateEmployee(id, addEmployeeDto);

        return new ResponseEntity<>(employeeDto,HttpStatus.OK);
    }

    @PatchMapping("/partial-update/{id}")
    public ResponseEntity<EmployeeDto> partialEmployeeUpdate(@PathVariable("id") String id, @RequestBody PartialUpdateEmployeeDto partialUpdateEmployeeDto){
        EmployeeDto employeeDto=employeeService.partialEmployeeUpdate(id, partialUpdateEmployeeDto);
        return new ResponseEntity<>(employeeDto,HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String id){
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Employee "+ id +" deleted successfully!",HttpStatus.OK);
    }
}
