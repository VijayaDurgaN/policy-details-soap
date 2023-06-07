package com.allstateonboarding.policydetailsjms.service;

import com.allstateonboarding.policydetailsjms.model.PolicyDetailsResult;
import com.allstateonboarding.policydetailssoap.generated.PolicyDetails;
import com.allstateonboarding.policydetailssoap.service.PolicyDetailsJmsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class PolicyDetailsConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(PolicyDetailsConsumerService.class);

    private final JmsTemplate template;
    private final String resultQueueName;

    @Autowired
    public PolicyDetailsConsumerService(
            JmsTemplate template,
            @Value("${spring.jms.policyDetailsResultQueueName}") String resultQueueName
    ) {
        this.template = template;
        this.resultQueueName = resultQueueName;
    }

    @JmsListener(destination = "${spring.jms.policyDetailsQueueName}")
    public void receive(PolicyDetailsJmsDTO policyDetails) {
        if (String.valueOf(policyDetails.getPolicyNumber()).length() > 8) {
            template.convertAndSend(
                    resultQueueName,
                    PolicyDetailsResult.builder()
                            .policyDetails(policyDetails)
                            .success(true)
                            .description("policy number more than 8 digits")
                            .build(),
                    message -> {
                        message.setStringProperty("success", "true");
                        return message;
                    }
            );
        } else {
            template.convertAndSend(
                    resultQueueName,
                    PolicyDetailsResult.builder()
                            .policyDetails(policyDetails)
                            .success(false)
                            .description("ERROR ! policy number less than or equal to` 8 digits")
                            .build(),
                    message -> {
                        message.setStringProperty("success", "false");
                        return message;
                    }
            );
        }
    }
}
