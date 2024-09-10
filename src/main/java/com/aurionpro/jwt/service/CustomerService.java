package com.aurionpro.jwt.service;

import java.util.List;

import com.aurionpro.jwt.dto.CustomerDto;
import com.aurionpro.jwt.dto.PageResponseDto;
import com.aurionpro.jwt.entity.Customer;

public interface CustomerService {

	Customer addCustomer(Customer customer);

	PageResponseDto<CustomerDto> getAllCustomers(int pageSize, int pageNumber);

	void updatePassword(int customerId, String newPassword);

}
