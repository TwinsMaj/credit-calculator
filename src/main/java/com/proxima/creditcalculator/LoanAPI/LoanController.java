package com.proxima.creditcalculator.LoanAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proxima.creditcalculator.error.CustomerNotFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoanController {
	@Autowired
	private final LoanService loanService;

	@CrossOrigin
	@PostMapping
	@RequestMapping("api/v1/loan-request")
	public ResponseEntity<Loan> getLoanDecision(@Valid @RequestBody Loan loanRequest) throws CustomerNotFoundException {
		log.info("fetching loan decision for customer: " + loanRequest.getId());
		Loan loanDecision = loanService.makeLoanDecision(loanRequest);
		return new ResponseEntity<Loan>(loanDecision, HttpStatus.OK);
	}
}
