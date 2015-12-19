package org.springframework.integration.loanbroker;

public class NodeIdentificationUtil {
	
	private static final String UNIQUE_NODE_INDENTIFIER = java.util.UUID.randomUUID().toString();
	
	
	public static String nodeIdentifier(){
		System.err.println("UNIQUE_INDENTIFIER  "+UNIQUE_NODE_INDENTIFIER);
		return UNIQUE_NODE_INDENTIFIER;
	}

}
