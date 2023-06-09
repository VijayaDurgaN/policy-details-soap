package com.allstateonboarding.policydetailssoap.repository

import com.allstateonboarding.policydetailssoap.exception.InternalServerError
import com.allstateonboarding.policydetailssoap.exception.PolicyNotFoundException
import com.allstateonboarding.policydetailssoap.generated.PolicyDetails
import org.slf4j.Logger
import spock.lang.Specification

import java.util.stream.Collectors

class PolicyDetailsRepositoryTest extends Specification {
    def reader = Mock(PolicyDetailsReader)
    def repository = new PolicyDetailsRepository(reader)
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
        policyDetail.claimNumber == claimNumber
    }

    def "should throw error when policy with given claim number is not found"() {
        given:
        def claimNumber = 120
        def mockLogger = Mock(Logger)
        repository.@logger = mockLogger

        when:
        repository.findByClaimNumber(claimNumber)

        then:
        1 * mockLogger.error("Policy with claim number 120 not found")
        1 * reader.readPolicyDetails() >> listOfPolicyDetails
        def exception = thrown(PolicyNotFoundException)
        exception.message == String.format("Policy with claim number %s not found", claimNumber)
    }

    def "should throw Internal Server Error when policy details could not be fetched"() {
        given:
        def claimNumber = 1233
        def mockLogger = Mock(Logger)
        repository.@logger = mockLogger

        when:
        repository.findByClaimNumber(claimNumber)

        then:
        1 * reader.readPolicyDetails() >> { throw new IOException() }
        def error = thrown(InternalServerError)
        error.message == "Error fetching policy details"
        1 * mockLogger.error("Error fetching policy details")
    }
}
