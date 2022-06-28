package com.example.roomwebapp.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class AddEmployeeDto {
    @Size(max = 64)
    private String firstName;
    @Size(max = 64)
    private String lastName;
    @Size(max = 64)
    private String position;
}
