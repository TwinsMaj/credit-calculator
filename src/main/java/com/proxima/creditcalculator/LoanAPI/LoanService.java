package com.proxima.creditcalculator.LoanAPI;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proxima.creditcalculator.engine.Decision;
import com.proxima.creditcalculator.engine.DecisionEngine;
import com.proxima.creditcalculator.engine.ScoringEngine;
import com.proxima.creditcalculator.engine.SimpleScoringEngine;
import com.proxima.creditcalculator.error.CustomerNotFoundException;
import com.proxima.creditcalculator.person.Person;

@Service
public class LoanService {

	@Autowired
	private LoanRepository loanRepository;

	public Loan makeLoanDecision(Loan loanRequest) throws CustomerNotFoundException {
		String customerId = loanRequest.getId();
		double loanAmount = loanRequest.getLoanAmount();
		int period = loanRequest.getPeriod();

		Optional<Person> customer = loanRepository.findPersonById(customerId);

		if(!customer.isPresent()) {
			throw new CustomerNotFoundException("Customer not found");
		}
		 
		ScoringEngine creditScore = new SimpleScoringEngine(customer.get().getCreditModifier(), loanAmount, period);
		Decision decision = new DecisionEngine(creditScore).makeDecision();

		return new Loan(customerId, period, loanAmount, decision.period(), decision.amount(), decision.message());
	}
}
