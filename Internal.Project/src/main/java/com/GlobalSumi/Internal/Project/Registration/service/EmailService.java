package com.GlobalSumi.Internal.Project.Registration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.GlobalSumi.Internal.Project.Registration.model.RegisterEmployee;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	private static final String RECIPIENT_EMAIL = "info@globalsumi.com";

	public void sendApprovalEmail(String to, Long userId, RegisterEmployee employee) {
		String approvalLink = "https://api.globalsumi.com/approve?userId=" + userId;

		String messageBody = "Dear " + employee.getFirstName() + " " + employee.getLastName() + ",\n\n"
				+ "Thank you for registering. Here are your details:\n\n" + "First Name: " + employee.getFirstName()
				+ "\n" + "Last Name: " + employee.getLastName() + "\n" + "Country Code: " + employee.getCountryCode()
				+ "\n" + "Phone Number: " + employee.getPhoneNumber() + "\n" + "Email: " + employee.getEmail() + "\n\n"
				+ "Please click the following link to approve your registration: " + approvalLink;

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(RECIPIENT_EMAIL);
		message.setSubject("Approve your registration");
		message.setText(messageBody);

		mailSender.send(message);
	}
}
