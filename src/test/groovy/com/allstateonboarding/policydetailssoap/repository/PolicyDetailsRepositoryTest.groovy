package com.allstateonboarding.policydetailssoap.repository

import com.allstateonboarding.policydetailssoap.exception.InternalServerError
import com.allstateonboarding.policydetailssoap.generated.PolicyDetails
import org.slf4j.Logger
import spock.lang.Specification

import java.util.stream.Collectors

class PolicyDetailsRepositoryTest extends Specification {
    private PolicyDetailsReader reader = Mock(PolicyDetailsReader)
    private PolicyDetailsRepository repository = new PolicyDetailsRepository(reader)
    def listOfPolicyDetails = List.of(
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

    def "should find policy details by claim number"() {
        given:
        def claimNumber = 1233

        when:
        def policyDetail = repository.findByClaimNumber(claimNumber)

        then:
        1 * reader.readPolicyDetails() >> listOfPolicyDetails
        policyDetail != Optional.empty()
        policyDetail.get().claimNumber == claimNumber
    }

    def "should return empty optional object when claim number does not exist"() {
        given:
        def claimNumber = 1230

        when:
        def policyDetail = repository.findByClaimNumber(claimNumber)


        then:
        1 * reader.readPolicyDetails() >> listOfPolicyDetails
        policyDetail == Optional.empty()
    }

    def "should throw Internal Server Error when policy details could not be fetched"() {
        given:
        def claimNumber = 1233
        def mockLogger = Mock(Logger)
        repository.logger = mockLogger
        when:
        repository.findByClaimNumber(claimNumber)
        then:
        1 * reader.readPolicyDetails() >> { throw new IOException() }
        def error = thrown(InternalServerError)
        error.message == "Error fetching policy details"
        1 * mockLogger.error("Error fetching policy details")
    }
}
