package com.aurionpro.jwt.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CustomerDto {
	private int customerId;
	@NotBlank(message = "First name is required")
	@Pattern(regexp = "^[A-Za-z]+$", message = "First name must not contain digits")
	private String firstName;

	@NotBlank(message = "Last name is required")
	@Pattern(regexp = "^[A-Za-z]+$", message = "Last name must not contain digits")
	private String lastName;

	@NotBlank(message = "Email is required")
	@Email(message = "Email should be valid")
	private String emailId;

	@Positive(message = "Mobile number must be a positive number")
	private long mobileNumber;

	private LocalDateTime createdAt;

	@NotBlank(message = "Password is required")
	private String password;
}
