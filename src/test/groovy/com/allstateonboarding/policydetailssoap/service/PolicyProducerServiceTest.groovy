package com.allstateonboarding.policydetailssoap.service

import com.allstateonboarding.policydetailssoap.generated.PolicyDetails
import org.modelmapper.ModelMapper
import org.springframework.jms.core.JmsTemplate
import spock.lang.Specification

class PolicyProducerServiceTest extends Specification {
    def "should add policy details to the queue when produce is called"() {
        given:
        def message = new PolicyDetails()
        message.claimNumber = 123

        String mockQueue = "mock-queue"
        def mockJmsTemplate = Mock(JmsTemplate)
        def modelMapper = new ModelMapper()
        PolicyDetailsJmsDTO policyDetailsSerializable = modelMapper.map(message, PolicyDetailsJmsDTO.class)
        def service = new PolicyProducerService(mockJmsTemplate, mockQueue)

        when:
        service.produce(message)

        then:
        1 * mockJmsTemplate.convertAndSend(mockQueue, policyDetailsSerializable)
    }
}
