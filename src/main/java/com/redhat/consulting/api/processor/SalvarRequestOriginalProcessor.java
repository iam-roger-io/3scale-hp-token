package com.redhat.consulting.api.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * This process saves the original request made to the Product, because it will be changed by Camel when invoking the token generation service.
 *
 */
@Component
public class SalvarRequestOriginalProcessor implements Processor {

	
	static final Logger logger = LoggerFactory.getLogger(SalvarRequestOriginalProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		
		logger.debug("#### Iniciando SalvarRequestOriginalProcessor " );

		exchange.setProperty("originalHeaders", exchange.getIn().getHeaders());
		exchange.setProperty("originalBody", exchange.getIn().getBody());
		exchange.setProperty("originalPath", exchange.getIn().getHeader(Exchange.HTTP_PATH));
		exchange.setProperty("originalHttpMethod", exchange.getIn().getHeader(Exchange.HTTP_METHOD));
	
		logger.debug("##### Salvo Token JWT Original : " + exchange.getIn().getHeader("Authorization", String.class));
		logger.debug("##### Salvo HTTP_PATH Original : " + exchange.getIn().getHeader(Exchange.HTTP_PATH, String.class));
		logger.debug("##### Salvo Request Body Original: " + exchange.getIn().getBody(String.class));		
		logger.debug("##### Salvo HTTP_METHOD Original : " + exchange.getIn().getHeader(Exchange.HTTP_METHOD));
			
	}

}
