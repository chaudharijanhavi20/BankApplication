package com.aurionpro.jwt.service;

import com.aurionpro.jwt.dto.AccountDto;
import com.aurionpro.jwt.entity.Account;

public interface AccountService {
	Account createAccount(AccountDto accountDto);
}
