package org.springframework.integration.loanbroker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class LoanBrokerController {
	
	@Autowired
	private LoanBrokerGateway loanBrokerGateway;	
	private static final double BEST_QUOTE_VALUE = 0.04;
	
    @RequestMapping("/quotation")
    public DeferredResult<ResponseEntity<?>> quotation(final @RequestParam(value="loanAmount", required=true) Double loanAmount) throws Exception {
    	
    	final DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<ResponseEntity<?>>(5000l);   	
    	deferredResult.onTimeout(new Runnable() {
			
			public void run() { // Retry on timeout
				deferredResult.setErrorResult(ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("Request timeout occurred."));
			}
		});
    	
    	ListenableFuture<Double> future = loanBrokerGateway.bestQuotation(loanAmount);
		future.addCallback(new ListenableFutureCallback<Double>() {

			public void onSuccess(Double result) {
				// Double check response matches with request
				if(result.equals(loanAmount * BEST_QUOTE_VALUE))
					deferredResult.setResult(ResponseEntity.ok(result));
				else
					deferredResult.setErrorResult(ResponseEntity.status(HttpStatus.CONFLICT).body("Invalid quotation "+result+" for loan amount "+loanAmount));
			}

			public void onFailure(Throwable t) {
				deferredResult.setErrorResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(t));
			}
		});				
		return deferredResult;
    }
}