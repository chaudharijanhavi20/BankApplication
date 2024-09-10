package com.aurionpro.jwt.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AccountDto {
	private int customerId;
	@NotNull(message = "Balance is required")
	@Positive(message = "Balance must be positive")
	private BigDecimal balance;

}
