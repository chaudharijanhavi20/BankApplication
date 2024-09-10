package com.aurionpro.jwt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurionpro.jwt.entity.Account;
import com.aurionpro.jwt.entity.Transaction;
import com.aurionpro.jwt.entity.TransactionType;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	List<Transaction> findByTransactionType(TransactionType transactionType);

	

	List<Transaction> findBySenderAccountNumberOrReceiverAccountNumber(String senderAccountNumber,
			String receiverAccountNumber);
}
