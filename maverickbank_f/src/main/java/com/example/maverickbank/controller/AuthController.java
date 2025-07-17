package com.example.maverickbank.controller;

import com.example.maverickbank.dto.AuthRequest;
import com.example.maverickbank.dto.AuthResponse;
import com.example.maverickbank.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            // Use username or accountNo as username
            String username = (request.getUsername() != null && !request.getUsername().isEmpty())
                ? request.getUsername()
                : (request.getAccountNo() != null ? String.valueOf(request.getAccountNo()) : null);

            if (username == null) {
                return ResponseEntity.badRequest().body("Username or accountNo required");
            }

            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, request.getPassword())
            );
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Extract role (assuming you have only one, adjust as needed)
            String role = userDetails.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "").toLowerCase();

            // Generate token WITH role as a claim
            String token = jwtUtil.generateTokenWithRole(userDetails, role);

            // Prepare the user object (send what frontend needs)
            Map<String, Object> user = new HashMap<>();
            user.put("username", userDetails.getUsername());
            user.put("role", role);

            // Respond with token AND user (this is what frontend expects!)
            Map<String, Object> resp = new HashMap<>();
            resp.put("token", token);
            resp.put("user", user);

            return ResponseEntity.ok(resp);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Login failed: " + e.getMessage());
        }
    }
}
