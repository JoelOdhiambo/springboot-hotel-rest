package com.example.roomwebapp.entity.employee.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class EmployeeDto {
    private String employeeId;
    @Size(max = 64)
    private String firstName;
    @Size(max = 64)
    private String lastName;
    @Size(max = 64)
    private String position;
}
