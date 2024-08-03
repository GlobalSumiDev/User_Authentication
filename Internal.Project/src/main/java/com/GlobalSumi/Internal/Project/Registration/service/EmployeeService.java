package com.GlobalSumi.Internal.Project.Registration.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.GlobalSumi.Internal.Project.Registration.Exception.EmailAlreadyInUseException;
import com.GlobalSumi.Internal.Project.Registration.model.RegisterEmployee;
import com.GlobalSumi.Internal.Project.Registration.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public RegisterEmployee registerUser(RegisterEmployee employee) {
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new EmailAlreadyInUseException("Email already in use");
        }

        // Encrypt the password
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        // Encrypt the confirmPassword
        employee.setConfirmpassword(passwordEncoder.encode(employee.getConfirmpassword()));

        RegisterEmployee savedEmployee = employeeRepository.save(employee);

        emailService.sendApprovalEmail(employee.getEmail(), savedEmployee.getId(), savedEmployee);

        return savedEmployee;
    }
}