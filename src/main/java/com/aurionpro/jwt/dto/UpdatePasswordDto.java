package com.aurionpro.jwt.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UpdatePasswordDto {

	@NotBlank(message = "New password is required")
	private String newPassword;

}
