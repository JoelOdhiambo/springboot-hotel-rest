package com.example.roomwebapp.entity.employee.model;

import com.example.roomwebapp.entity.employee.dto.EmployeeDto;
import com.example.roomwebapp.enums.Position;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Employee")
@Getter
@Setter
public class Employee {
    @Id
    @Column(name="EMPLOYEE_ID")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String  employeeId;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "POSITION")
    private String position;

    public Employee() {
        this.employeeId= UUID.randomUUID().toString();
    }

    public Employee(String employeeId, String firstName, String lastName, Position position) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = String.valueOf(position);
    }

    public EmployeeDto toEmployeeDto(){
        EmployeeDto employeeDto =new EmployeeDto();
        employeeDto.setEmployeeId(this.employeeId);
        employeeDto.setFirstName(this.firstName);
        employeeDto.setLastName(this.lastName);
        employeeDto.setPosition(this.position);

        return employeeDto;
    }
}
