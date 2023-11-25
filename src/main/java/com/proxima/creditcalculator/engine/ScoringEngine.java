package com.proxima.creditcalculator.engine;

import org.springframework.stereotype.Component;

@Component
public class ScoringEngine {

	public double calculateCreditScore(int creditModifier, double loanAmount, int loanPeriod) {
		return (creditModifier / loanAmount) * loanPeriod;
	}
}