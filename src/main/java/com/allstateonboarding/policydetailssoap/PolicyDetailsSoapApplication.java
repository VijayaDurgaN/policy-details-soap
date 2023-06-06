package com.allstateonboarding.policydetailssoap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan("com.allstateonboarding.policydetailssoap")
@PropertySource("classpath:soap-application.properties")
public class PolicyDetailsSoapApplication {

	public static void main(String[] args) {
		SpringApplication.run(PolicyDetailsSoapApplication.class, args);
	}

}
