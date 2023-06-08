package com.allstateonboarding.policydetailsrest.configuration;

import com.allstateonboarding.policydetailsrest.client.PolicyDetailsSoapClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SoapClientConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.allstateonboarding.policydetailsrest.generated");
        return marshaller;
    }

    @Bean
    public PolicyDetailsSoapClient policyDetailsSoapClient(Jaxb2Marshaller marshaller) {
        PolicyDetailsSoapClient client = new PolicyDetailsSoapClient();
        client.setDefaultUri("http://localhost:8080/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}