package com.proxima.creditcalculator.engine;

public class SimpleScoringEngine extends ScoringEngine {
	public SimpleScoringEngine() {
		super();
	}

	public SimpleScoringEngine(int creditModifier, double loanAmount, int loanPeriod) {
		super(creditModifier, loanAmount, loanPeriod);
	}

	public double calculateCreditScore() {
		double creditScore = (getCreditModifier() / getLoanAmount()) * getLoanPeriod();
		return Math.floor(creditScore * 100) / 100;
	}
}