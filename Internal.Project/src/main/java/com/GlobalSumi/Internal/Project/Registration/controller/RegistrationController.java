package com.GlobalSumi.Internal.Project.Registration.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.GlobalSumi.Internal.Project.Registration.AWSLambdaHandler.LambdaHandler;
import com.GlobalSumi.Internal.Project.Registration.Exception.EmailAlreadyInUseException;
import com.GlobalSumi.Internal.Project.Registration.model.RegisterEmployee;
import com.GlobalSumi.Internal.Project.Registration.repository.EmployeeRepository;
import com.GlobalSumi.Internal.Project.Registration.service.EmployeeService;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class RegistrationController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeRepository employeeRepository;
	

//	@PostMapping("/register")
//	public ResponseEntity<Map<String, String>> registerUser(@RequestBody RegisterEmployee employee) {
//		Map<String, String> response = new HashMap<>();
//		try {
//			employeeService.registerUser(employee);
//			response.put("responseMessage", "Registration successful");
//			return new ResponseEntity<>(response, HttpStatus.CREATED);
//		} catch (Exception e) {
//			response.put("responseMessage", "Registration failed: " + e.getMessage());
//			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//		}
//	}
	
	 @PostMapping("/register")
	    public ResponseEntity<Map<String, String>> registerUser(@RequestBody RegisterEmployee employee) {
	        Map<String, String> response = new HashMap<>();
	        try {
	            employeeService.registerUser(employee);
	            response.put("responseMessage", "Registration successful");
	            return new ResponseEntity<>(response, HttpStatus.CREATED);
	        } catch (EmailAlreadyInUseException e) {
	            response.put("responseMessage", "Email already in use");
	            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	        } catch (Exception e) {
	            response.put("responseMessage", "Registration failed: " + e.getMessage());
	            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	        }
	    }


	@GetMapping("/approve")
	public ResponseEntity<String> approveUser(@RequestParam Long userId) {
		RegisterEmployee employee = employeeRepository.findById(userId).orElse(null);
		if (employee != null) {
			employee.setApprovalStatus("Y");
			employeeRepository.save(employee);
			return new ResponseEntity<>("Approval successful, The user is registered", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
	}
}
