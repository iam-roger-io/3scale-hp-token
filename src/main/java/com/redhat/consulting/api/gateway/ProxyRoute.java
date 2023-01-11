package com.redhat.consulting.api.gateway;

import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.redhat.consulting.api.model.Access;
import com.redhat.consulting.api.processor.MontarRequestTokenHPProcessor;
import com.redhat.consulting.api.processor.RestaurarRequestOriginalProcessor;
import com.redhat.consulting.api.processor.SalvarRequestOriginalProcessor;

/**
 * Cria o proxy apra processar a requisição do Product.
 * 
 *
 */
@Component
public class ProxyRoute extends RouteBuilder {

	@Autowired
	private SalvarRequestOriginalProcessor salvarRequestOriginal;
	
	@Autowired
	private RestaurarRequestOriginalProcessor restaurarRequestOriginal;
	
	@Autowired
	private MontarRequestTokenHPProcessor montarRequestTokenHP;
	
	static final Logger logger = LoggerFactory.getLogger(ProxyRoute.class);

	@Override
	public void configure() throws Exception {

		logger.debug("########## Iniciando ProxyRoute" + System.getenv().get("TESTE"));
		
		final RouteDefinition from;
		if (Files.exists(keystorePath())) {
			from = from(
					"netty4-http:proxy://0.0.0.0:8443?ssl=true&keyStoreFile=/tls/keystore.jks&passphrase=changeit&trustStoreFile=/tls/keystore.jks");
		} else {
			from = from("netty4-http:proxy://0.0.0.0:8088");
		}

		from
		.process(salvarRequestOriginal)
	    .to("direct:get-token")
		.log("##### PROXY: netty4-http:" 
				+ "${headers." + Exchange.HTTP_SCHEME + "}://" 
				+ "${headers." + Exchange.HTTP_HOST + "}:" 
				+ "${headers." + Exchange.HTTP_PORT + "}" 
				+ "${headers." + Exchange.HTTP_PATH + "}")		
	
		.toD("netty4-http:" 
				+ "${headers." + Exchange.HTTP_SCHEME + "}://" 
				+ "${headers." + Exchange.HTTP_HOST + "}:" 
				+ "${headers." + Exchange.HTTP_PORT + "}" 
				+ "${headers." + Exchange.HTTP_PATH + "}");
	
		
	
		from("direct:get-token")
		.setHeader(Exchange.HTTP_METHOD, constant("POST"))
		.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
		.setHeader(Exchange.HTTP_PATH, constant(""))		
		.process(montarRequestTokenHP)
		.log(LoggingLevel.DEBUG, "### Endopint para solicitacao de token:  ${headers.hpTokenUrl}")
		.toD("${headers.hpTokenUrl}")
		.unmarshal().json(JsonLibrary.Jackson, Access.class)
		.process(restaurarRequestOriginal);
	
	}

	Path keystorePath() {
		return Path.of("/tls", "keystore.jks");
	}
	
}