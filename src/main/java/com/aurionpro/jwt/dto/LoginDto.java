package com.aurionpro.jwt.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class LoginDto {
	@NotEmpty(message = "Username is required")
	@Pattern(regexp = "^[a-zA-Z0-9_]{3,20}$", message = "Username must be between 3 and 20 characters and can contain letters, numbers, and underscores")
	private String username;
	@NotEmpty(message = "Password is required")
	private String password;
}
