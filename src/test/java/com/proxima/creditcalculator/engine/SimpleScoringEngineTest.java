package com.proxima.creditcalculator.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

@Component
public class SimpleScoringEngineTest {
	private ScoringEngine  simpleScoringEngine;

	@Test
	void testCalculateCreditScore() {
		double expectedCreditScore = 2.40;

		simpleScoringEngine = new SimpleScoringEngine(100, 1_000.00, 24);
		double actualCreditScore = simpleScoringEngine.calculateCreditScore();
		assertEquals(actualCreditScore, expectedCreditScore);
	}
}
