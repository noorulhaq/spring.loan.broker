package org.springframework.integration.loanbroker;

import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BankStub {
	

	private Double interestRate = 0.08d;
	
	public static final Logger LOGGER = LoggerFactory.getLogger(BankStub.class);  
	
	public Double quotation(Double loanAmount) throws Exception{
		
		// Seeding partial failure to verify request timeout. 
		if(loanAmount <= 10d && interestRate==0.05d){
			throw new IllegalArgumentException("loan amount should be greater than "+loanAmount);
		}
		
		int sleepTime = new Random().nextInt((1500 - 1000) + 1) + 1000;
		Thread.sleep(sleepTime);
		LOGGER.info(" >> Callculating best quotation for price {} is {}. Where approx. calculation time is {} ms.",loanAmount,(loanAmount * interestRate),sleepTime);
		return loanAmount * interestRate;
	}
	
	
	public void setInterestRate(String interestRate) {
		this.interestRate = Double.valueOf(interestRate);
	}

}
