package com.aurionpro.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aurionpro.jwt.dto.AccountDto;
import com.aurionpro.jwt.entity.Account;
import com.aurionpro.jwt.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/app")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PostMapping("/accounts")
	public ResponseEntity<Account> createAccount(@Valid @RequestBody AccountDto accountDto) {
		Account newAccount = accountService.createAccount(accountDto);
		return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
	}
}
