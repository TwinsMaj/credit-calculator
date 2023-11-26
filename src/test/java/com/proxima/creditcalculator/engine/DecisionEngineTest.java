package com.proxima.creditcalculator.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DecisionEngineTest {
	private DecisionEngine decisionEngine;
	private ScoringEngine simpleScoringEngine;

	@BeforeEach
	void setUp() {
		simpleScoringEngine =  new SimpleScoringEngine();
		decisionEngine = new DecisionEngine(simpleScoringEngine);
	}

	@Test
	@DisplayName("should return positive decision when credit score is greater than one")
	void whenCreditScoreGreaterThanOne() {
		simpleScoringEngine.setLoanPeriod(30);
		simpleScoringEngine.setCreditModifier(100);
		simpleScoringEngine.setLoanAmount(2_500);

		int period = simpleScoringEngine.getLoanPeriod();
		Decision expectedDecision = new Decision("€2,500.00", period, "Positive Decision");
		Decision actualDecision = decisionEngine.makeDecision();

		assertEquals(expectedDecision.amount(), actualDecision.amount());
		assertEquals(expectedDecision.message(), actualDecision.message());
	}

	@Test
	@DisplayName("should return the maximum sum when loan amount greater than the maximum")
	void whenLoanAmountGreaterThanMax() {
		simpleScoringEngine.setLoanPeriod(40);
		simpleScoringEngine.setCreditModifier(300);
		simpleScoringEngine.setLoanAmount(11_000);

		int period = simpleScoringEngine.getLoanPeriod();
		Decision expectedDecision = new Decision("€10,000.00", period, "Positive Decision");
		Decision actualDecision = decisionEngine.makeDecision();

		assertEquals(expectedDecision.amount(), actualDecision.amount());
		assertEquals(expectedDecision.message(), actualDecision.message());
	}

	@Test
	@DisplayName("should return suitable amount when credit score is low")
	void findSuitableAmountForLowCreditScore() {
		simpleScoringEngine.setLoanPeriod(33);
		simpleScoringEngine.setCreditModifier(300);
		simpleScoringEngine.setLoanAmount(11_000);

		int period = simpleScoringEngine.getLoanPeriod();
		Decision expectedDecision = new Decision("€9,000.00", period, "Positive Decision");
		Decision actualDecision = decisionEngine.makeDecision();

		assertEquals(expectedDecision.amount(), actualDecision.amount());
		assertEquals(expectedDecision.message(), actualDecision.message());
	}

	@Test
	@DisplayName("should return suitable period when credit score is low and no suitable amount")
	void findSuitablePeriod() {
		simpleScoringEngine.setLoanPeriod(4);
		simpleScoringEngine.setCreditModifier(300);
		simpleScoringEngine.setLoanAmount(2_500);

		String msg = "Positive Decision, Try a loan period of 60 months";
		Decision expectedDecision = new Decision("€2,500.00", 60, msg);
		Decision actualDecision = decisionEngine.makeDecision();

		assertEquals(expectedDecision.amount(), actualDecision.amount());
		assertEquals(expectedDecision.period(), actualDecision.period());
		assertEquals(expectedDecision.message(), actualDecision.message());
	}
}
