package com.allstateonboarding.policydetailsrest.service

import com.allstateonboarding.policydetailsrest.client.SoapClient
import com.allstateonboarding.policydetailsrest.generated.GetPolicyDetailsResponse
import com.allstateonboarding.policydetailsrest.generated.PolicyDetails
import spock.lang.Specification

class PolicyDetailsServiceTest extends Specification {
    def "should get policy details given claim number"() {
        given:
        def claimNumber = 123
        def mockSoapClient = Mock(SoapClient)
        def service = new PolicyDetailsService(mockSoapClient)

        def policyDetailsResponse = new GetPolicyDetailsResponse()
        def policyDetails = new PolicyDetails()
        policyDetails.claimNumber = claimNumber
        policyDetailsResponse.setPolicyDetails(policyDetails)

        when:
        def policyDetailsFromService = service.getPolicyDetails(claimNumber)

        then:
        1 * mockSoapClient.sendSoapRequest(_) >> policyDetailsResponse
        policyDetailsFromService.claimNumber == claimNumber
    }
}
