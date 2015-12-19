package org.springframework.integration.loanbroker;

import org.springframework.util.concurrent.ListenableFuture;


public interface LoanBrokerGateway {
	
	public ListenableFuture<Double> bestQuotation(Double loanAmount);

}
