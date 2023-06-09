package com.allstateonboarding.policydetailsrest.service

import com.allstateonboarding.policydetailsrest.client.PolicyDetailsSoapClient
import com.allstateonboarding.policydetailsrest.exception.BadRequestException
import com.allstateonboarding.policydetailsrest.exception.PolicyNotFoundException
import com.allstateonboarding.policydetailsrest.exception.ServiceUnavailableException
import com.allstateonboarding.policydetailsrest.generated.GetPolicyDetailsResponse
import com.allstateonboarding.policydetailsrest.generated.PolicyDetails
import org.springframework.ws.client.WebServiceIOException
import org.springframework.ws.soap.client.SoapFaultClientException
import spock.lang.Specification

import javax.xml.namespace.QName

class PolicyDetailsServiceTest extends Specification {

    def mockSoapClient = Mock(PolicyDetailsSoapClient)
    def service = new PolicyDetailsService(mockSoapClient)

    def "should get policy details given claim number"() {
        given:
        def claimNumber = 123
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

    def "should throw service unavailable exception when soap service connection is refused"() {
        given:
        1 * mockSoapClient.sendSoapRequest(_) >> {
            throw new WebServiceIOException("mock-exception")
        }

        when:
        service.getPolicyDetails(12345678)

        then:
        def serviceUnavailableException = thrown(ServiceUnavailableException)
        serviceUnavailableException.message == "Service unavailable"
    }

    def "should throw soap fault exception when soap service gives fault"() {
        given:
        def exception = Mock(SoapFaultClientException)
        def expectedErrorMessage = "claim number abc is invalid"
        1 * exception.getFaultCode() >> new QName("http://example.com", "MOCK_ERROR")
        1 * exception.getFaultStringOrReason() >> expectedErrorMessage
        1 * mockSoapClient.sendSoapRequest(_) >> {
            throw exception
        }

        when:
        service.getPolicyDetails(12345678)

        then:
        def soapFaultException = thrown(BadRequestException)
        soapFaultException.message == "Invalid soap request : ${expectedErrorMessage}"
    }

    def "should throw policy not found exception when soap fault code is POLICY_NOT_FOUND_EXCEPTION"() {
        given:
        def exception = Mock(SoapFaultClientException)
        def expectedErrorMessage = "Policy with claim number 12345678 not found"
        1 * exception.getFaultCode() >> new QName("http://example.com", "POLICY_NOT_FOUND_EXCEPTION")
        1 * exception.getFaultStringOrReason() >> expectedErrorMessage
        1 * mockSoapClient.sendSoapRequest(_) >> {
            throw exception
        }

        when:
        service.getPolicyDetails(12345678)

        then:
        def soapFaultException = thrown(PolicyNotFoundException)

        soapFaultException.message == expectedErrorMessage
    }

    def "should throw service unavailable exception when soap fault code is INTERNAL_SERVER_ERROR"() {
        given:
        def exception = Mock(SoapFaultClientException)
        def expectedErrorMessage = "Error fetching policy details"
        1 * exception.getFaultCode() >> new QName("http://example.com", "INTERNAL_SERVER_ERROR")
        1 * exception.getFaultStringOrReason() >> expectedErrorMessage
        1 * mockSoapClient.sendSoapRequest(_) >> {
            throw exception
        }

        when:
        service.getPolicyDetails(12345678)

        then:
        def soapFaultException = thrown(ServiceUnavailableException)

        soapFaultException.message == expectedErrorMessage
    }
}
