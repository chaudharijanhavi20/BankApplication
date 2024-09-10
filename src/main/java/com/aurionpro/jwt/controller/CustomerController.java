package com.aurionpro.jwt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.jwt.dto.CustomerDto;
import com.aurionpro.jwt.dto.PageResponseDto;
import com.aurionpro.jwt.dto.UpdatePasswordDto;
import com.aurionpro.jwt.entity.Customer;
import com.aurionpro.jwt.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/app")
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@PostMapping("/customers")
	public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
		Customer newCustomer = customerService.addCustomer(customer);
		return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
	}

	

	@GetMapping("/customers")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PageResponseDto<CustomerDto>> getAllCustomers(
			@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize) {

		PageResponseDto<CustomerDto> customerPage = customerService.getAllCustomers(pageSize, pageNumber);
		return new ResponseEntity<>(customerPage, HttpStatus.OK);
	}

	   @PatchMapping("/{customerId}/password")
    public ResponseEntity<String> updatePassword(@PathVariable int customerId,
                                                 @RequestBody @Valid UpdatePasswordDto updatePasswordDto) {

        customerService.updatePassword(customerId, updatePasswordDto.getNewPassword());

        return ResponseEntity.ok("Password updated successfully for customer ID: " + customerId);
    }
	}

