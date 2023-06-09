package com.allstateonboarding.policydetailssoap.endpoint

import com.allstateonboarding.policydetailssoap.exception.InternalServerError
import com.allstateonboarding.policydetailssoap.exception.PolicyNotFoundException
import com.allstateonboarding.policydetailssoap.generated.GetPolicyDetailsResponse
import com.allstateonboarding.policydetailssoap.generated.PolicyDetails
import com.allstateonboarding.policydetailssoap.service.PolicyDetailsService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.webservices.server.AutoConfigureMockWebServiceClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.ws.test.server.MockWebServiceClient
import org.springframework.xml.transform.StringSource
import spock.lang.Specification

import static org.springframework.ws.test.server.RequestCreators.withPayload
import static org.springframework.ws.test.server.ResponseMatchers.*

@SpringBootTest
@AutoConfigureMockWebServiceClient
class PolicyDetailsEndpointTest extends Specification {

    @Autowired
    private MockWebServiceClient client

    @SpringBean
    private PolicyDetailsService mockPolicyDetailsService = Mock(PolicyDetailsService)

    def "should get PolicyDetails when claim number is valid"() {
        def getPolicyDetailsResponse = new GetPolicyDetailsResponse()
        def policyDetails = new PolicyDetails()
        policyDetails.claimNumber = 12345678
        policyDetails.policyHolderName = "Vijaya"
        policyDetails.policyNumber = 123456
        policyDetails.coverageLimit = 10000
        policyDetails.coverageName = "Durga"
        policyDetails.deductible = 500
        getPolicyDetailsResponse.policyDetails = policyDetails
        1 * mockPolicyDetailsService.getPolicyDetailsByClaimNumber(12345678) >> getPolicyDetailsResponse

        expect:
        StringSource request = new StringSource(
                "<ns:GetPolicyDetailsRequest xmlns:ns='http://allstate.com/policy'>" +
                        "<ns:claimNumber>12345678</ns:claimNumber>" +
                        "</ns:GetPolicyDetailsRequest>"
        )
        StringSource response = new StringSource(
                "<ns:GetPolicyDetailsResponse xmlns:ns='http://allstate.com/policy'>" +
                        "<ns:PolicyDetails>" +
                        "<ns:claimNumber>12345678</ns:claimNumber>" +
                        "<ns:policyHolderName>Vijaya</ns:policyHolderName>" +
                        "<ns:policyNumber>123456</ns:policyNumber>" +
                        "<ns:coverageName>Durga</ns:coverageName>" +
                        "<ns:coverageLimit>10000</ns:coverageLimit>" +
                        "<ns:deductible>500</ns:deductible>" +
                        "</ns:PolicyDetails>" +
                        "</ns:GetPolicyDetailsResponse>"
        )
        client.sendRequest(withPayload(request))
                .andExpect(noFault())
                .andExpect(validPayload(new ClassPathResource("xsd/policy-details.xsd")))
                .andExpect(payload(response))
    }

    def "should return internal server soap fault when fetching policy details fails"() {

        1 * mockPolicyDetailsService.getPolicyDetailsByClaimNumber(12345678) >> {
            throw new InternalServerError("Error fetching policy details")
        }

        expect:
        StringSource request = new StringSource(
                "<ns:GetPolicyDetailsRequest xmlns:ns='http://allstate.com/policy'>" +
                        "<ns:claimNumber>12345678</ns:claimNumber>" +
                        "</ns:GetPolicyDetailsRequest>"
        )
        client.sendRequest(withPayload(request))
                .andExpect(
                        xpath("//faultcode")
                                .evaluatesTo("ns0:INTERNAL_SERVER_ERROR")
                )
                .andExpect(
                        xpath("//faultstring")
                                .evaluatesTo("Error fetching policy details")
                )
//                .andExpect(serverOrReceiverFault("Error fetching policy details"))
    }

    def "should return policy not found soap fault when policy with claim number does not exist"() {

        1 * mockPolicyDetailsService.getPolicyDetailsByClaimNumber(12345678) >> {
            throw new PolicyNotFoundException("Policy with claim number 12345678 not found")
        }

        expect:
        StringSource request = new StringSource(
                "<ns:GetPolicyDetailsRequest xmlns:ns='http://allstate.com/policy'>" +
                        "<ns:claimNumber>12345678</ns:claimNumber>" +
                        "</ns:GetPolicyDetailsRequest>"
        )
        client.sendRequest(withPayload(request))
                .andExpect(
                        xpath("//faultcode")
                                .evaluatesTo("ns0:POLICY_NOT_FOUND_EXCEPTION")
                )
                .andExpect(
                        xpath("//faultstring")
                                .evaluatesTo("Policy with claim number 12345678 not found")
                )
    }
}
