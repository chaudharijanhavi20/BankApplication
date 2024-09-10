package com.aurionpro.jwt.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "customers")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customerId")
	private int customerId;
	@Column(name = "customerFirstName")
	@NotBlank(message = "First name is required")
	@Pattern(regexp = "^[A-Za-z]+$", message = "First name must not contain digits")
	private String customerFirstName;
	@Column(name = "customerLastName")
	@NotBlank(message = "Last name is required")
	@Pattern(regexp = "^[A-Za-z]+$", message = "Last name must not contain digits")
	private String customerLastName;
	@Positive(message = "Mobile number must be a positive number")
	@Column(name = "mobileNumber")
	private long mobileNumber;
	@Column(nullable = false, unique = true)
	@NotBlank(message = "Email is required")
	@Email(message = "Email should be valid")
	private String emailId;
	@Column(nullable = false)
	private String password;
	@Column
	private LocalDateTime createdAt;

//	@Column(name = "is_active")
//	private boolean active = true; // Default to true
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "userId")
	private User user;

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
	}

	public Customer(int customerId, String customerFirstName, String customerLastName, long mobileNumber,
			String emailId, User user) {
		this.customerId = customerId;
		this.customerFirstName = customerFirstName;
		this.customerLastName = customerLastName;
		this.mobileNumber = mobileNumber;
		this.emailId = emailId;
		this.user = user;
	}

	// Default constructor required by JPA
	public Customer() {
	}


}
