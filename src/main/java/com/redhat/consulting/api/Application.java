package com.redhat.consulting.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties

@SpringBootApplication
@ImportResource({"classpath:spring/camelContext.xml"})
@PropertySource(value="classpath:application.properties",  ignoreResourceNotFound = true )
@PropertySource(value="/deployments/application.properties",  ignoreResourceNotFound = true )
public class Application {
	static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
    	
    	logger.debug("Iniciando");
    	
    	
        SpringApplication.run(Application.class, args);
    }
}
