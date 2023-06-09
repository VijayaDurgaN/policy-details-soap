package com.allstateonboarding.policydetailssoap.config;

import com.allstateonboarding.policydetailssoap.repository.PolicyDetailsReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReaderConfig {
    @Bean
    public PolicyDetailsReader policyDetailsReader()
    {
        return new PolicyDetailsReader("mock_policy_details_.json");
    }
}
