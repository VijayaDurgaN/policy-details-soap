package com.allstateonboarding.policydetailssoap.endpoint


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


    def "should get PolicyDetails when claim number is valid"() {
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

}
