package com.proxima.creditcalculator.engine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ScoringEngine {
	public int creditModifier;
	public double loanAmount;
	public int loanPeriod;
	
	public abstract double calculateCreditScore();
}