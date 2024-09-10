package com.aurionpro.jwt.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transactions")
@RequiredArgsConstructor
@Getter
@Setter
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;

	private String senderAccountNumber;

	private String receiverAccountNumber;

	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;

	private BigDecimal amount;

	private LocalDateTime transactionDate;

	@ManyToOne
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	@PrePersist
	protected void onCreate() {
		transactionDate = LocalDateTime.now();
	}
}