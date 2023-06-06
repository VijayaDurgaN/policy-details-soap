package com.allstateonboarding.policydetails.service

import com.allstateonboarding.policydetails.exception.PolicyNotFoundException
import com.allstateonboarding.policydetails.generated.PolicyDetails
import com.allstateonboarding.policydetails.repository.PolicyDetailsRepository
import spock.lang.Specification

class PolicyDetailsServiceTest extends Specification {

    private PolicyDetailsRepository repo = Mock(PolicyDetailsRepository)
    private PolicyDetailsService service = new PolicyDetailsService(repo)

    def "should get policy details when claim number is valid"() {
        given:
        def claimNumber = 1233
        def policyDetails = new PolicyDetails()
        policyDetails.setClaimNumber(1233)

        when:
        def response = service.getPolicyDetailsByClaimNumber(claimNumber)

        then:
        1 * repo.findByClaimNumber(claimNumber) >> Optional.of(policyDetails)
        response.policyDetails.claimNumber == claimNumber

    }

    def "should throw error when claim number is invalid"() {
        given:
        def claimNumber = 120

        when:
        service.getPolicyDetailsByClaimNumber(claimNumber)

        then:
        1 * repo.findByClaimNumber(claimNumber) >> Optional.empty()
        def exception = thrown(PolicyNotFoundException)
        exception.message == String.format("Policy with claim number %s not found", claimNumber)
    }
}
