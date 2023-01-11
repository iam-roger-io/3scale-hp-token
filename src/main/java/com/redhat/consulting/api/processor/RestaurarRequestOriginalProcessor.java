package com.redhat.consulting.api.processor;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.redhat.consulting.api.model.Access;

/**
 * This process restores the Original request made on the Product.
 *
 */
@Component
public class RestaurarRequestOriginalProcessor implements Processor {

	
	static final Logger logger = LoggerFactory.getLogger(RestaurarRequestOriginalProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {

		logger.debug("#### Iniciando RestaurarRequestOriginalProcessor " );

		Access body = exchange.getIn().getBody(Access.class);
		
		//Se token nulo erro
		
		exchange.getOut().setHeaders((Map<String, Object>) exchange.getProperty("originalHeaders"));
	    exchange.getOut().setBody(exchange.getProperty("originalBody"));
	    exchange.getOut().setHeader(Exchange.HTTP_PATH, exchange.getProperty("originalPath"));
	    exchange.getOut().setHeader(Exchange.HTTP_METHOD, exchange.getProperty("originalHttpMethod"));
	    
	    exchange.getOut().setHeader("Authorization", body.getToken().getId());
	    
	    logger.debug("##### Recuperando Exchange.HTTP_HOST Original: " + exchange.getOut().getHeader(Exchange.HTTP_HOST));
		logger.debug("##### Recuperando Exchange.HTTP_PATH Original : " + exchange.getOut().getHeader(Exchange.HTTP_PATH));
		logger.debug("##### Recuperando Request Body Original : " + exchange.getOut().getBody(String.class));
		logger.debug("##### Recuperando HTTP_METHOD Original  : " + exchange.getOut().getHeader(Exchange.HTTP_METHOD));
		logger.debug("##### Novo Token JWT: " + exchange.getOut().getHeader("Authorization", String.class));
				
			
	}

}
