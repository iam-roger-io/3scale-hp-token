package com.redhat.consulting.api.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
/**
 * This process retrieves the URL and access credentials to the token generation service, and assembles the JSON to be sent in the request.
 * This data needs to be in a Openshift secret
 * 
 */
public class MontarRequestTokenHPProcessor implements Processor{

	static final Logger logger = LoggerFactory.getLogger(MontarRequestTokenHPProcessor.class);
	
	@Override
	public void process(Exchange exchange) throws Exception {

		String username = System.getenv().get("TOKEN_USERNAME");			
		String password = System.getenv().get("TOKEN_PASSWORD");
		String tenantName = System.getenv().get("TOKEN_TENANT_NAME");
		String tokenURL= System.getenv().get("TOKEN_URL");						
		
		logger.debug("### URL para solicitar token: " + tokenURL);
								
		tokenURL = tokenURL.replace("http:", "http4:"); // E se for nulo
		tokenURL = tokenURL.replace("https:", "https4:");
		tokenURL = tokenURL.trim() + "?bridgeEndpoint=true";

		String json = "{\r\n" 
				+ "   \"auth\": {\r\n" 
				+ "      \"passwordCredentials\": {\r\n"
				+ "          \"username\": \"" + username+ "\",\r\n" 
				+ "          \"password\": \""+ password + "\"\r\n"
				+ "       },\r\n" 
				+ "   \"tenantName\": \"" + tenantName + "\"\r\n" 
				+ "}}";						

		exchange.getIn().setHeader("hpTokenUrl", tokenURL);	
		exchange.getIn().setBody(json);
		
	}

}
