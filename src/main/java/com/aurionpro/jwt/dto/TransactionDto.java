package com.aurionpro.jwt.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.aurionpro.jwt.entity.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TransactionDto {

	private Long transactionId;

	private String senderAccountNumber;

	private String receiverAccountNumber;
	@NotNull(message = "Transaction type is required")
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	@NotNull(message = "Amount is required")
	@Positive(message = "Amount must be positive")
	private BigDecimal amount;

	private LocalDateTime transactionDate;
}
