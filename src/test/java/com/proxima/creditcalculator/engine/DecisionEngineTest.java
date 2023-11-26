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
		simpleScoringEngine.setLoanPeriod(24);
		simpleScoringEngine.setCreditModifier(100);
		simpleScoringEngine.setLoanAmount(1_000);

		String expectedDecision = "Positive Decision, €1,000.00";
		String actualDecision = decisionEngine.makeDecision();

		assertEquals(expectedDecision, actualDecision);
	}

	@Test
	@DisplayName("should return the maximum sum when loan amount greater than the maximum")
	void whenLoanAmountGreaterThanMax() {
		simpleScoringEngine.setLoanPeriod(40);
		simpleScoringEngine.setCreditModifier(300);
		simpleScoringEngine.setLoanAmount(11_000);

		String expectedDecision = "Positive Decision, €10,000.00";
		String actualDecision = decisionEngine.makeDecision();

		assertEquals(expectedDecision, actualDecision);
	}

	@Test
	@DisplayName("should return suitable amount when credit score is low")
	void findSuitableAmountForLowCreditScore() {
		simpleScoringEngine.setLoanPeriod(33);
		simpleScoringEngine.setCreditModifier(300);
		simpleScoringEngine.setLoanAmount(11_000);

		String expectedDecision = "Positive decision, €9,000.00";
		String actualDecision = decisionEngine.makeDecision();

		assertEquals(expectedDecision, actualDecision);
	}

	@Test
	@DisplayName("should return suitable period when credit score is low and no suitable amount")
	void findSuitablePeriod() {
		simpleScoringEngine.setLoanPeriod(2);
		simpleScoringEngine.setCreditModifier(300);
		simpleScoringEngine.setLoanAmount(1_000);

		String expectedDecision = "Postive decision, €1,000.00, Try a loan period of 60 months";
		String actualDecision = decisionEngine.makeDecision();

		assertEquals(expectedDecision, actualDecision);
	}
}
