package com.allstateonboarding.policydetails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.allstateonboarding.policydetails")
public class PolicyDetailsSoapApplication {

	public static void main(String[] args) {
		SpringApplication.run(PolicyDetailsSoapApplication.class, args);
	}

}
