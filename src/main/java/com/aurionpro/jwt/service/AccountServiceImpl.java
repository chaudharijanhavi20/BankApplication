package com.aurionpro.jwt.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurionpro.jwt.dto.AccountDto;
import com.aurionpro.jwt.entity.Account;
import com.aurionpro.jwt.entity.Customer;
import com.aurionpro.jwt.repository.AccountRepository;
import com.aurionpro.jwt.repository.CustomerRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Account createAccount(AccountDto accountDto) {
		Customer customer = customerRepository.findById(accountDto.getCustomerId())
				.orElseThrow(() -> new RuntimeException("Customer not found"));

		Account account = new Account();
		account.setCustomer(customer);
		account.setBalance(accountDto.getBalance());
		account.setAccountNumber(generateAccountNumber());

		return accountRepository.save(account);
	}

	private String generateAccountNumber() {
		return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
	}
}
