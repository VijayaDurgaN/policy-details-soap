package com.allstateonboarding.policydetails.repository

import com.allstateonboarding.policydetails.exception.InternalServerError
import com.allstateonboarding.policydetails.generated.PolicyDetails
import spock.lang.Specification

import java.util.stream.Collectors

class PolicyDetailsRepositoryTest extends Specification {
    private PolicyDetailsReader reader = Mock(PolicyDetailsReader)
    private PolicyDetailsRepository repository = new PolicyDetailsRepository(reader)

    def "should find policydetails by claim number"() {
        given:
        def claimNumber = 1233

        when:
        def policyDetail = repository.findByClaimNumber(claimNumber)

        then:
        1 * reader.readPolicyDetails() >> List.of(
                1233,
                1234,
                1235
        ).stream().map(
                claimNumber1 -> {
                    def policyDetails = new PolicyDetails()
                    policyDetails.setClaimNumber(claimNumber1)
                    return policyDetails
                }
        ).collect(Collectors.toList())
        policyDetail != Optional.empty()
        policyDetail.get().claimNumber == claimNumber
    }

    def "should throw Internal Server Error when policy details could not be fetched"() {
        given:
        def claimNumber = 1233
        when:
        repository.findByClaimNumber(claimNumber)
        then:
        1 * reader.readPolicyDetails() >> { throw new IOException() }
        def error = thrown(InternalServerError)
        error.message == "Error fetching policy details"
    }
}
