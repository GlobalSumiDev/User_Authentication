package com.GlobalSumi.Internal.Project.Registration.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.GlobalSumi.Internal.Project.Registration.model.RegisterEmployee;
import com.GlobalSumi.Internal.Project.Registration.repository.EmployeeRepository;

@Service
public class EmployeeDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeDetailsService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RegisterEmployee employee = employeeRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        // Check if the approval status is set to "Y"
        if (!"Y".equals(employee.getApprovalStatus())) {
            logger.warn("User with email: {} has not been approved yet.", username);
            throw new UsernameNotFoundException("User not approved yet. Please contact support.");
        }

        logger.info("Loading user with email: {}", username);
        logger.info("Stored encoded password: {}", employee.getPassword());

        return User.withUsername(employee.getEmail())
                .password(employee.getPassword()) // The password should already be encoded
                .passwordEncoder(password -> "{bcrypt}" + password) // Ensure this matches the password encoding
                .authorities("USER")
                .build();
    }
}