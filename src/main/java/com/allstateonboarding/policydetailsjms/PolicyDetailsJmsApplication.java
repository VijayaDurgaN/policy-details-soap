package com.allstateonboarding.policydetailsjms;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.EnableJms;


@PropertySource("classpath:jms-application.properties")
@SpringBootApplication
@ComponentScan("com.allstateonboarding.policydetailsjms")
@EnableJms
public class PolicyDetailsJmsApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(PolicyDetailsJmsApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

}