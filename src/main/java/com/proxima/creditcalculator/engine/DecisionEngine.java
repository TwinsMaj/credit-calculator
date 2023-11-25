package com.proxima.creditcalculator.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DecisionEngine {
	private static final double CREDIT_SCORE_MIN = 1;
	private static final int MIN_LOAN_PERIOD = 12;
	private static final int MAX_LOAN_PERIOD = 60;
	private static final double MIN_INPUT_OUTPUT_SUM = 2_000;
	private static final double MAX_INPUT_OUTPUT_SUM = 10_000;

	@Autowired
	ScoringEngine simpleScoringEngine;

	public double makeDecision(int creditModifier, double loanAmount, int loanPeriod) {
		double result = -1;

		double creditScore = simpleScoringEngine.calculateCreditScore(creditModifier, loanAmount, loanPeriod);

		if (creditScore >= CREDIT_SCORE_MIN) {
			result = loanAmount;
		} else {
			for(double amount = MAX_INPUT_OUTPUT_SUM; amount >= MIN_INPUT_OUTPUT_SUM; amount -= 1_000) {
				credit_score = simpleScoringEngine.calculateCreditScore(creditModifier, loanAmount, loanPeriod);
			}
		}
		return result;
	}

}