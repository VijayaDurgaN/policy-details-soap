package com.allstateonboarding.policydetailssoap.service;


import com.allstateonboarding.policydetailssoap.generated.PolicyDetails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class PolicyProducerService {

    private final JmsTemplate jmsTemplate;

    private final String policyDetailsQueue;

    @Autowired
    public PolicyProducerService(
            JmsTemplate jmsTemplate,
            @Value("spring.jms.policyDetailsQueueName") String policyDetailsQueue
    ) {
        this.jmsTemplate = jmsTemplate;
        this.policyDetailsQueue = policyDetailsQueue;
    }

    void produce(PolicyDetails policyDetails) {
        ModelMapper modelMapper = new ModelMapper();
        PolicyDetailsJmsDTO serializable = modelMapper.map(policyDetails, PolicyDetailsJmsDTO.class);
        jmsTemplate.convertAndSend(policyDetailsQueue, serializable);
    }
}
