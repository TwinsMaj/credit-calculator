package com.proxima.creditcalculator.LoanAPI;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.proxima.creditcalculator.error.CustomerNotFoundException;
import com.proxima.creditcalculator.person.Person;

@SpringBootTest
public class LoanServiceTest {
	@Autowired
	private LoanService loanService;

	@MockBean
	private LoanRepository loanRepository;

	@Test
	@DisplayName("Should return positive response to customer")
	void testMakeLoanDecision() throws CustomerNotFoundException {
		String expectedDecision = "Positive Decision";
		String expectedApprovedLoanAmount = "€2,500.00";

		Loan loanRequest = new Loan(); 
		loanRequest.setId("49002010976");
		loanRequest.setPeriod(30);
		loanRequest.setLoanAmount(2_500.00);

		String customerId = loanRequest.getId();

		Person customer = new Person("segment", 100);
		Optional<Person> opt = Optional.ofNullable(customer);

		Mockito.when(loanRepository.findPersonById(customerId)).thenReturn(opt);
		Loan loanDecision = loanService.makeLoanDecision(loanRequest);

		assertEquals(expectedDecision, loanDecision.getDecision());
		assertEquals(expectedApprovedLoanAmount, loanDecision.getApprovedLoanAmount());
	}

	@Test
	@DisplayName("should return negaive decision if user has debt")
	void testLoanDecisionWhenCustomerHasDebt() throws CustomerNotFoundException {
		String expectedDecision = "Negative Decision";
		String expectedApprovedLoanAmount = "0.00";
		
		Loan loanRequest = new Loan(); 
		loanRequest.setId("49002010965");
		loanRequest.setPeriod(24);
		loanRequest.setLoanAmount(1_000.00);

		String customerId = loanRequest.getId();

		Person customer = new Person("debt", 0);
		Optional<Person> opt = Optional.ofNullable(customer);

		Mockito.when(loanRepository.findPersonById(customerId)).thenReturn(opt);
		Loan loanDecision = loanService.makeLoanDecision(loanRequest);

		assertEquals(expectedDecision, loanDecision.getDecision());
		assertEquals(expectedApprovedLoanAmount, loanDecision.getApprovedLoanAmount());
	}
}
