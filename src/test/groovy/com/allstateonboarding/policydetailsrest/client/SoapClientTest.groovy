package com.allstateonboarding.policydetailsrest.client

import com.allstateonboarding.policydetailsrest.generated.GetPolicyDetailsRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.webservices.client.AutoConfigureMockWebServiceServer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.ws.test.client.MockWebServiceServer
import org.springframework.ws.test.client.RequestMatchers
import org.springframework.ws.test.client.ResponseCreators
import org.springframework.xml.transform.StringSource
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockWebServiceServer
class SoapClientTest extends Specification {

    @Autowired
    PolicyDetailsSoapClient client

    def "Name"() {
        given:
        def mockServer = MockWebServiceServer.createServer(client)
        mockServer.expect(
                RequestMatchers.payload(
                        new StringSource("<ns:GetPolicyDetailsRequest xmlns:ns='http://allstate.com/policy'>" +
                                "<ns:claimNumber>12345678</ns:claimNumber>" +
                                "</ns:GetPolicyDetailsRequest>")
                )
        ).andRespond(
                ResponseCreators.withPayload(
                        new StringSource(
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
                )
        )
        def request = new GetPolicyDetailsRequest()
        request.setClaimNumber(12345678)

        when:
        def actual = client.sendSoapRequest(request)


        def details = actual.getPolicyDetails()
        then:
        details.getClaimNumber() == 12345678
        details.getPolicyHolderName() == "Vijaya"
        mockServer.verify()
    }
}

