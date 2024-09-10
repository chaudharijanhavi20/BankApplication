package com.aurionpro.jwt.service;

import com.aurionpro.jwt.dto.LoginDto;
import com.aurionpro.jwt.dto.RegistrationDto;
import com.aurionpro.jwt.entity.User;

public interface AuthService {
	
	User register(RegistrationDto registration);
	String login(LoginDto loginDto);

}
