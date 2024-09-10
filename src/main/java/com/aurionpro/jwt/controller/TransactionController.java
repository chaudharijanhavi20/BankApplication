package com.aurionpro.jwt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.aurionpro.jwt.dto.TransactionDto;
import com.aurionpro.jwt.entity.TransactionType;
import com.aurionpro.jwt.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/app")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping("/transactions")
	public ResponseEntity<TransactionDto> performTransaction(@RequestBody TransactionDto transactionDto) {
		TransactionDto result = transactionService.performTransaction(transactionDto);
		return ResponseEntity.ok(result);
	}

	@GetMapping("transactions")
	public ResponseEntity<List<TransactionDto>> getAllTransactions() {
		List<TransactionDto> transactions = transactionService.getAllTransactions();
		return ResponseEntity.ok(transactions);
	}

	@GetMapping("/type/{transactionType}")
	public ResponseEntity<List<TransactionDto>> getTransactionsByType(@PathVariable String transactionType) {
		List<TransactionDto> transactions = transactionService
				.getTransactionsByType(Enum.valueOf(TransactionType.class, transactionType.toUpperCase()));
		return ResponseEntity.ok(transactions);
	}
	@GetMapping("/passbook/{accountNumber}")
    public ResponseEntity<List<TransactionDto>> getPassbook(@PathVariable String accountNumber) {
        List<TransactionDto> transactions = transactionService.getTransactionsForAccount(accountNumber);
        if (transactions.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no transactions found
        }
        return ResponseEntity.ok(transactions); // Return 200 OK with the transaction list
    }
}
