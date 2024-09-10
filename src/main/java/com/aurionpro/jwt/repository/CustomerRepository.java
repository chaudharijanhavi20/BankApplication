package com.aurionpro.jwt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurionpro.jwt.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	

	
}
