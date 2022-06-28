package com.example.roomwebapp.web;

import com.example.roomwebapp.service.EmployeeServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;

    public EmployeeController(EmployeeServiceImpl employeeService){
        this.employeeService=employeeService;
    }

    @GetMapping
    public String getAllEmployees(Model model){
        model.addAttribute("employees",employeeService.getAllEmployees());
        return "employees";
    }
}
