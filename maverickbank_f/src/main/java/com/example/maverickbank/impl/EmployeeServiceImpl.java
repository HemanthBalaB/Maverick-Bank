package com.example.maverickbank.impl;

import com.example.maverickbank.dto.EmployeeDTO;
import com.example.maverickbank.model.Employee;
import com.example.maverickbank.repo.EmployeeRepository;
import com.example.maverickbank.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Employee createEmployee(EmployeeDTO dto) {
        Employee emp = new Employee();
        emp.setName(dto.getName());
        emp.setEmail(dto.getEmail());
        emp.setMobileNo(dto.getMobileNo());
        emp.setPassword(passwordEncoder.encode(dto.getPassword()));
        emp.setAddress(dto.getAddress());
        emp.setRole("EMPLOYEE");
        return employeeRepository.save(emp);
    }

    @Override
    public Employee getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }
    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }


    @Override
    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Employee getEmployeeByMobileNo(String mobileNo) {
        return employeeRepository.findByMobileNo(mobileNo).orElse(null);
    }
}
