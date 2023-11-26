package com.proxima.creditcalculator.engine;

import java.text.DecimalFormat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class DecisionEngine {
	private static final double MIN_CREDIT_SCORE = 1;
	private static final int MIN_LOAN_PERIOD = 12;
	private static final int MAX_LOAN_PERIOD = 60;
	private static final double MIN_INPUT_OUTPUT_SUM = 2_000;
	private static final double MAX_INPUT_OUTPUT_SUM = 10_000;

	private ScoringEngine simpleScoringEngine;

	public Decision makeDecision() {
		DecimalFormat formatter = new DecimalFormat("€0,000.00");
		double creditScore = simpleScoringEngine.calculateCreditScore();
		int period = simpleScoringEngine.getLoanPeriod();
		
		if (creditScore >= MIN_CREDIT_SCORE) {
			double loanAmount = determineLoanAmount(simpleScoringEngine.getLoanAmount());
			return  new Decision(formatter.format(loanAmount), period, "Positive Decision");
		} else {
			double suitableLoanAmount = findSuitableAmount();
			
			if(suitableLoanAmount > 0) {
				return new Decision(formatter.format(suitableLoanAmount), period, "Positive Decision");
			} else {
				int suitablePeriod = findSuitablePeriod();
				if(suitablePeriod > 0) {
					double loan = simpleScoringEngine.getLoanAmount();
					String msg = "Positive Decision, Try a loan period of " + suitablePeriod + " months";
					return new Decision(formatter.format(loan), suitablePeriod, msg);
				}
			}
		}
		return new Decision("0.00", 0, "Negative Decision");
	}

	private double determineLoanAmount(double loanAmount) {
		return loanAmount > MAX_INPUT_OUTPUT_SUM ? MAX_INPUT_OUTPUT_SUM : loanAmount;
	}

	private int findSuitablePeriod() {
		int initialLoanPeriod = simpleScoringEngine.getLoanPeriod();

		for(int period = MAX_LOAN_PERIOD; period > MIN_LOAN_PERIOD; --period) {
			simpleScoringEngine.setLoanPeriod(period);
			double creditScore = simpleScoringEngine.calculateCreditScore();
			if(creditScore >= MIN_CREDIT_SCORE) {
				simpleScoringEngine.setLoanPeriod(initialLoanPeriod);
				return period;
			}
		}

		simpleScoringEngine.setLoanPeriod(initialLoanPeriod);

		return 0;
	}

	private double findSuitableAmount() {
		double initialLoanAmount = simpleScoringEngine.getLoanAmount();

		for(double amount = MAX_INPUT_OUTPUT_SUM; amount >= MIN_INPUT_OUTPUT_SUM; amount -= 1_000) {
			simpleScoringEngine.setLoanAmount(amount);
			double creditScore = simpleScoringEngine.calculateCreditScore();
			if(creditScore >= MIN_CREDIT_SCORE) {
				simpleScoringEngine.setLoanAmount(initialLoanAmount);
				return Math.floor(amount * 100) / 100;
			}
		}

		simpleScoringEngine.setLoanAmount(initialLoanAmount);

		return 0;
	}
}