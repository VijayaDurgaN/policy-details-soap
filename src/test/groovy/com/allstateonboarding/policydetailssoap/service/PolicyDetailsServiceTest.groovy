package com.allstateonboarding.policydetailssoap.service


import com.allstateonboarding.policydetailssoap.generated.PolicyDetails
import com.allstateonboarding.policydetailssoap.repository.PolicyDetailsRepository
import org.slf4j.Logger
import spock.lang.Specification

class PolicyDetailsServiceTest extends Specification {

    def repo = Mock(PolicyDetailsRepository)
    def producerService = Mock(PolicyProducerService)
    def service = new PolicyDetailsService(repo, producerService)

    def "should get policy details when claim number is valid"() {
        given:
        def claimNumber = 1233
        def mockLogger = Mock(Logger)
        service.@logger = mockLogger
        def policyDetails = new PolicyDetails()
        policyDetails.setClaimNumber(1233)

        when:
        def response = service.getPolicyDetailsByClaimNumber(claimNumber)

        then:
        1 * repo.findByClaimNumber(claimNumber) >> policyDetails
        1 * producerService.produce(policyDetails)
        1 * mockLogger.info("Fetching policy details for claim number {}", claimNumber)
        1 * mockLogger.info("Fetching policy details for claim number {} completed", claimNumber)
        response.policyDetails.claimNumber == claimNumber
    }
}
