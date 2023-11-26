package com.proxima.creditcalculator.LoanAPI;


import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityScan
public class Loan {
	@NotBlank(message = "Please provide customer id")
	private String id;

	@NotNull(message = "Please provide loan period")
	private int period;

	@NotNull(message = "Please provide loan amount")
	@DecimalMax("10000.0") @DecimalMin("2000.0") 
	private double loanAmount;

	private int approvedPeriod;
	private String approvedLoanAmount;
	private String decision;
}
