package com.aurionpro.jwt.service;

import java.util.List;

import com.aurionpro.jwt.dto.TransactionDto;
import com.aurionpro.jwt.entity.TransactionType;

public interface TransactionService {
	TransactionDto performTransaction(TransactionDto transactionDto);

	List<TransactionDto> getAllTransactions();

	List<TransactionDto> getTransactionsByType(TransactionType transactionType);

	List<TransactionDto> getTransactionsForAccount(String accountNumber);
}
