package com.aurionpro.jwt.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aurionpro.jwt.dto.CustomerDto;
import com.aurionpro.jwt.dto.PageResponseDto;
import com.aurionpro.jwt.entity.Customer;
import com.aurionpro.jwt.repository.CustomerRepository;

import jakarta.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class CustomerServiceImpl implements CustomerService {
	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	
	public Customer addCustomer(Customer customer) {
		logger.info("Adding customer: {}", customer);
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		return customerRepository.save(customer);
	}



	@Override
	@Transactional
	public void updatePassword(int customerId, String newPassword) {
		  logger.info("Updating customer password {}", customerId);
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer not found"));

		System.out.println("Updating password for customer ID: " + customerId);
		System.out.println("New password: " + newPassword);

		customer.setPassword(passwordEncoder.encode(newPassword));

		customerRepository.save(customer);
	}

	public PageResponseDto<CustomerDto> getAllCustomers(int pageSize, int pageNumber) {
		logger.debug("Fetching all customers");
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Customer> customerPage = customerRepository.findAll(pageable);

		PageResponseDto<CustomerDto> customerPageDto = new PageResponseDto<>();
		customerPageDto.setTotalPages(customerPage.getTotalPages());
		customerPageDto.setTotalElements(customerPage.getNumberOfElements());
		customerPageDto.setSize(customerPage.getSize());
		customerPageDto.setContent(customerPage.map(this::convertToCustomerDto).getContent());
		customerPageDto.setLastPage(customerPage.isLast());

		return customerPageDto;
	}

	private CustomerDto convertToCustomerDto(Customer customer) {
		return new CustomerDto(customer.getCustomerId(), customer.getCustomerFirstName(),
				customer.getCustomerLastName(), customer.getEmailId(), customer.getMobileNumber(),
				customer.getCreatedAt(), customer.getPassword());
	}
}
