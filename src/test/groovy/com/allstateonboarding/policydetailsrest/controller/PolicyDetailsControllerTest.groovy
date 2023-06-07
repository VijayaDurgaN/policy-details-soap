package com.allstateonboarding.policydetailsrest.controller

import com.allstateonboarding.policydetailsrest.client.SoapClient
import com.allstateonboarding.policydetailsrest.generated.GetPolicyDetailsResponse
import com.allstateonboarding.policydetailsrest.generated.PolicyDetails
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class PolicyDetailsControllerTest extends Specification {

    def mockPolicySoapClient = Mock(SoapClient)
    def controller = new PolicyDetailsController(mockPolicySoapClient)

    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build()

    def "should get policy details when claim number is valid"() {
        given:
        def response = new GetPolicyDetailsResponse()
        def details = new PolicyDetails()
        details.claimNumber = 12345678
        details.policyHolderName = "mock-policy-holder-name"
        details.coverageName = "mock-coverage-name"
        response.policyDetails = details

        when:
        mockMvc.perform(
                get("/policy-details/by-claim-number/12345678")
                        .accept("application/json")
        )
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().json("{\"policyDetails\":{\"claimNumber\":12345678,\"policyHolderName\":\"mock-policy-holder-name\",\"policyNumber\":0,\"coverageName\":\"mock-coverage-name\",\"coverageLimit\":0,\"deductible\":0}}"))
        then:
        1 * mockPolicySoapClient.sendSoapRequest(_) >> response
    }
}
