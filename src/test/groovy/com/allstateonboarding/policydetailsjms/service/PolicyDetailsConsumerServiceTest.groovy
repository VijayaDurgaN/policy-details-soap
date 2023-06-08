package com.allstateonboarding.policydetailsjms.service

import com.allstateonboarding.policydetailsjms.model.PolicyDetailsJmsDTO
import com.allstateonboarding.policydetailsjms.model.PolicyDetailsResult
import org.springframework.jms.core.JmsTemplate
import spock.lang.Specification

class PolicyDetailsConsumerServiceTest extends Specification {

    def mockTemplate = Mock(JmsTemplate)
    def mockResultQueueName = "mock-result-queue"

    def service = new PolicyDetailsConsumerService(mockTemplate, mockResultQueueName)


    def "should send policy details result to the result queue when received a policy from the request queue"() {
        when:
        service.receive(policyDetailsRequest)

        then:
        1 * mockTemplate.convertAndSend(mockResultQueueName, policyDetailsResult)

        where:
        policyDetailsRequest                 | policyDetailsResult
        policyWithNumberLessThan8Digits()    | policyWithNumberLessThan8DigitsResult()
        policyWithNumberEqualTo8Digits()     | policyWithNumberEqualTo8DigitsResult()
        policyWithNumberGreaterThan8Digits() | policyWithNumberGreaterThan8DigitsResult()
    }

    def policyWithNumberLessThan8DigitsResult() {
        return PolicyDetailsResult.builder()
                .policyDetails(policyWithNumberLessThan8Digits())
                .success(false)
                .description("ERROR ! policy number less than or equal to` 8 digits")
                .build()
    }

    def policyWithNumberEqualTo8DigitsResult() {
        return PolicyDetailsResult.builder()
                .policyDetails(policyWithNumberEqualTo8Digits())
                .success(false)
                .description("ERROR ! policy number less than or equal to` 8 digits")
                .build()
    }

    def policyWithNumberGreaterThan8DigitsResult() {
        return PolicyDetailsResult.builder()
                .policyDetails(policyWithNumberGreaterThan8Digits())
                .success(true)
                .description("policy number more than 8 digits")
                .build()
    }

    def policyWithNumberLessThan8Digits() {
        def policy = new PolicyDetailsJmsDTO()
        policy.setPolicyNumber(1234)
        return policy
    }

    def policyWithNumberEqualTo8Digits() {
        def policy = new PolicyDetailsJmsDTO()
        policy.setPolicyNumber(12345678)
        return policy
    }

    def policyWithNumberGreaterThan8Digits() {
        def policy = new PolicyDetailsJmsDTO()
        policy.setPolicyNumber(1234567890)
        return policy
    }
}
