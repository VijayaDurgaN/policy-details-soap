package com.allstateonboarding.policydetailsjms.service;

import com.allstateonboarding.policydetailsjms.model.PolicyDetailsJmsDTO;
import com.allstateonboarding.policydetailsjms.model.PolicyDetailsResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class PolicyDetailsConsumerService {
    public static final String POLICY_RESULT_SUCCESS_DESCRIPTION = "policy number more than 8 digits";
    public static final String POLICY_RESULT_FAILURE_DESCRIPTION = "ERROR ! policy number less than or equal to` 8 digits";

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
    public void receive(TextMessage textMessage) throws JsonProcessingException, JMSException {
        String policyDetailsText = textMessage.getText();
        PolicyDetailsJmsDTO policyDetails = new ObjectMapper().readValue(policyDetailsText, PolicyDetailsJmsDTO.class);
        if (String.valueOf(policyDetails.getPolicyNumber()).length() > 8) {
            sendPolicyResult(policyDetails, true, POLICY_RESULT_SUCCESS_DESCRIPTION);
        } else {
            sendPolicyResult(policyDetails, false, POLICY_RESULT_FAILURE_DESCRIPTION);
        }
    }

    private void sendPolicyResult(PolicyDetailsJmsDTO policyDetails, boolean success, String description) {
        template.convertAndSend(
                resultQueueName,
                PolicyDetailsResult.builder()
                        .policyDetails(policyDetails)
                        .success(success)
                        .description(description)
                        .build()
        );
    }
}
