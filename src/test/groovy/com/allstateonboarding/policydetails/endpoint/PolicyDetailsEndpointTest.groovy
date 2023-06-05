package com.allstateonboarding.policydetails.endpoint

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.webservices.server.WebServiceServerTest
import org.springframework.core.io.ClassPathResource
import org.springframework.test.context.ContextConfiguration
import org.springframework.ws.test.server.MockWebServiceClient
import org.springframework.xml.transform.StringSource
import spock.lang.Specification

import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.noFault;
import static org.springframework.ws.test.server.ResponseMatchers.payload;
import static org.springframework.ws.test.server.ResponseMatchers.validPayload;
import static org.springframework.ws.test.server.ResponseMatchers.xpath;

@WebServiceServerTest
@ContextConfiguration
class PolicyDetailsEndpointTest extends Specification {
    @Autowired
    private MockWebServiceClient client;

    def "should get PolicyDetails when claim number is valid"() {
        expect:

        StringSource request = new StringSource(
                "<ns:GetPolicyDetailsRequest xmlns:ns='http://allstate.com/policy'>" +
                        "<ns:claimNumber>1233</ns:claimNumber>" +
                        "</ns:GetPolicyDetailsRequest>"
        );
        StringSource response = new StringSource(
                "<ns:GetPolicyDetailsResponse xmlns:ns='http://allstate.com/policy'>" +
                        "<ns:PolicyDetails>" +
                        "<ns:claimNumber>1233</ns:claimNumber>" +
                        "<ns:policyHolderName>Policy Holder Name</ns:policyHolderName>" +
                        "<ns:policyNumber>12345678</ns:policyNumber>" +
                        "<ns:coverageName>Coverage Name</ns:coverageName>" +
                        "<ns:coverageLimit>200000</ns:coverageLimit>" +
                        "<ns:deductible>1000</ns:deductible>" +
                        "</ns:PolicyDetails>" +
                        "</ns:GetPolicyDetailsResponse>"
        );

        client.sendRequest(withPayload(request))
                .andExpect(noFault())
                .andExpect(validPayload(new ClassPathResource("xsd/policy-details.xsd")))
                .andExpect(payload(response))
    }
}
