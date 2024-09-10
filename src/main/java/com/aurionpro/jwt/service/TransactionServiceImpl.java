package com.aurionpro.jwt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aurionpro.jwt.dto.TransactionDto;
import com.aurionpro.jwt.entity.Account;
import com.aurionpro.jwt.entity.Transaction;
import com.aurionpro.jwt.entity.TransactionType;
import com.aurionpro.jwt.repository.AccountRepository;
import com.aurionpro.jwt.repository.TransactionRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
	private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	@Transactional
	public TransactionDto performTransaction(TransactionDto transactionDto) {
		// Depending on the transaction type, validate and perform the transaction
		switch (transactionDto.getTransactionType()) {
		case CREDIT:
			return performCredit(transactionDto);
		case DEBIT:
			return performDebit(transactionDto);
		case TRANSFER:
			return performTransfer(transactionDto);
		default:
			throw new IllegalArgumentException("Invalid transaction type");
		}
	}

	private TransactionDto performCredit(TransactionDto transactionDto) {
		logger.debug("Performing credit transaction: {}", transactionDto);
		Optional<Account> senderAccountOpt = accountRepository
				.findByAccountNumber(transactionDto.getSenderAccountNumber());
		if (!senderAccountOpt.isPresent()) {
			throw new RuntimeException("Sender account not found");
		}

		Account senderAccount = senderAccountOpt.get();
		Transaction transaction = new Transaction();
		transaction.setAccount(senderAccount);
		transaction.setAmount(transactionDto.getAmount());
		transaction.setTransactionType(TransactionType.CREDIT);
		transaction.setSenderAccountNumber(transactionDto.getSenderAccountNumber());

		senderAccount.setBalance(senderAccount.getBalance().add(transaction.getAmount()));
		accountRepository.save(senderAccount);

		transactionRepository.save(transaction);
		return convertToDto(transaction);
	}

	private TransactionDto performDebit(TransactionDto transactionDto) {
		 logger.debug("Performing debit transaction: {}", transactionDto);

		Optional<Account> receiverAccountOpt = accountRepository
				.findByAccountNumber(transactionDto.getReceiverAccountNumber());
		if (!receiverAccountOpt.isPresent()) {
			throw new RuntimeException("Receiver account not found");
		}

		Account receiverAccount = receiverAccountOpt.get();
		Transaction transaction = new Transaction();
		transaction.setAccount(receiverAccount);
		transaction.setAmount(transactionDto.getAmount());
		transaction.setTransactionType(TransactionType.DEBIT);
		transaction.setReceiverAccountNumber(transactionDto.getReceiverAccountNumber());

		if (receiverAccount.getBalance().compareTo(transaction.getAmount()) < 0) {
			throw new RuntimeException("Insufficient balance");
		}
		receiverAccount.setBalance(receiverAccount.getBalance().subtract(transaction.getAmount()));
		accountRepository.save(receiverAccount);

		transactionRepository.save(transaction);
		return convertToDto(transaction);
	}

	private TransactionDto performTransfer(TransactionDto transactionDto) {
		 logger.debug("Performing transfer transaction: {}", transactionDto);
		if (transactionDto.getSenderAccountNumber().equals(transactionDto.getReceiverAccountNumber())) {
			throw new RuntimeException("Sender and receiver cannot be the same account");
		}

		// Find sender's account
		Optional<Account> senderAccountOpt = accountRepository
				.findByAccountNumber(transactionDto.getSenderAccountNumber());
		if (!senderAccountOpt.isPresent()) {
			throw new RuntimeException("Sender account not found");
		}

		// Find receiver's account
		Optional<Account> receiverAccountOpt = accountRepository
				.findByAccountNumber(transactionDto.getReceiverAccountNumber());
		if (!receiverAccountOpt.isPresent()) {
			throw new RuntimeException("Receiver account not found");
		}

		Account senderAccount = senderAccountOpt.get();
		Account receiverAccount = receiverAccountOpt.get();

		// Debit from sender
		if (senderAccount.getBalance().compareTo(transactionDto.getAmount()) < 0) {
			throw new RuntimeException("Insufficient balance in sender's account");
		}
		senderAccount.setBalance(senderAccount.getBalance().subtract(transactionDto.getAmount()));
		accountRepository.save(senderAccount);

		// Credit to receiver
		receiverAccount.setBalance(receiverAccount.getBalance().add(transactionDto.getAmount()));
		accountRepository.save(receiverAccount);

		// Save the debit transaction for the sender
		Transaction senderTransaction = new Transaction();
		senderTransaction.setAccount(senderAccount);
		senderTransaction.setAmount(transactionDto.getAmount());
		senderTransaction.setTransactionType(TransactionType.DEBIT);
		senderTransaction.setSenderAccountNumber(transactionDto.getSenderAccountNumber());
		senderTransaction.setReceiverAccountNumber(transactionDto.getReceiverAccountNumber()); // Set receiver's account
																								// number
		transactionRepository.save(senderTransaction);

		// Save the credit transaction for the receiver
		Transaction receiverTransaction = new Transaction();
		receiverTransaction.setAccount(receiverAccount);
		receiverTransaction.setAmount(transactionDto.getAmount());
		receiverTransaction.setTransactionType(TransactionType.CREDIT);
		receiverTransaction.setSenderAccountNumber(transactionDto.getSenderAccountNumber()); // Set sender's account
																								// number
		receiverTransaction.setReceiverAccountNumber(transactionDto.getReceiverAccountNumber());
		transactionRepository.save(receiverTransaction);

		// Returning details of the debit transaction for the sender
		return convertToDto(senderTransaction);
	}

	private TransactionDto convertToDto(Transaction transaction) {
		TransactionDto dto = new TransactionDto();
		dto.setTransactionId(transaction.getTransactionId());
		dto.setSenderAccountNumber(transaction.getSenderAccountNumber());
		dto.setReceiverAccountNumber(transaction.getReceiverAccountNumber());
		dto.setTransactionType(transaction.getTransactionType());
		dto.setAmount(transaction.getAmount());
		dto.setTransactionDate(transaction.getTransactionDate());
		return dto;
	}

	@Override
	public List<TransactionDto> getAllTransactions() {
		List<Transaction> transactions = transactionRepository.findAll();
		return transactions.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	@Override
	public List<TransactionDto> getTransactionsByType(TransactionType transactionType) {
		List<Transaction> transactions = transactionRepository.findByTransactionType(transactionType);
		return transactions.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	@Override
	public List<TransactionDto> getTransactionsForAccount(String accountNumber) {
		List<Transaction> transactions = transactionRepository
				.findBySenderAccountNumberOrReceiverAccountNumber(accountNumber, accountNumber);
		return transactions.stream().map(this::convertToDto).collect(Collectors.toList());
	}
}
