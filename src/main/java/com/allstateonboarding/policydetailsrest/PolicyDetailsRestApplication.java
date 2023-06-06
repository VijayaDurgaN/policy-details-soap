package com.allstateonboarding.policydetailsrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@ComponentScan("com.allstateonboarding.policydetailsrest")
@PropertySource("rest-application.properties")
public class PolicyDetailsRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PolicyDetailsRestApplication.class, args);
    }

}
