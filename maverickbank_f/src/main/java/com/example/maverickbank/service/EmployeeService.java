package com.example.maverickbank.service;

import com.example.maverickbank.dto.EmployeeDTO;
import com.example.maverickbank.model.Employee;
import java.util.List;

public interface EmployeeService {
    Employee createEmployee(EmployeeDTO dto);
    Employee getEmployeeById(Long employeeId);
    Employee getEmployeeByEmail(String email);
    Employee getEmployeeByMobileNo(String mobileNo);
    List<Employee> getAllEmployees();
}
