package com.example.maverickbank.controller;

import com.example.maverickbank.dto.EmployeeDTO;
import com.example.maverickbank.model.Employee;
import com.example.maverickbank.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import jakarta.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody EmployeeDTO dto) {
        Employee emp = employeeService.createEmployee(dto);
        return ResponseEntity.ok("Employee registered with ID: " + emp.getEmployeeId());
    }
    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee emp = employeeService.getEmployeeById(id);
        return (emp != null) ? ResponseEntity.ok(emp) : ResponseEntity.notFound().build();
    }

    // ✅ Employee login (session-based)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData, HttpSession session) {
        String email = loginData.get("username"); // Accepts 'username' as key
        if (email == null) email = loginData.get("email"); // fallback if needed

        String password = loginData.get("password");

        Employee employee = employeeService.getEmployeeByEmail(email);

        // ✅ Use plain text password match (no encoder)
        if (employee != null && passwordEncoder.matches(password, employee.getPassword())) {
            session.setAttribute("employee", employee);
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

    }

    // ✅ Logout
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out successfully");
    }

    // ✅ Optional session check
    @GetMapping("/session-check")
    public ResponseEntity<?> checkSession(HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }
    }
}
