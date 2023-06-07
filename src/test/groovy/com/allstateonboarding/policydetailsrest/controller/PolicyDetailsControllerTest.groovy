package com.allstateonboarding.policydetailsrest.controller

import com.allstateonboarding.policydetailsrest.dto.PolicyDetailsDTO
import com.allstateonboarding.policydetailsrest.service.PolicyDetailsService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class PolicyDetailsControllerTest extends Specification {

    def mockPolicyDetailsService = Mock(PolicyDetailsService)
    def controller = new PolicyDetailsController(mockPolicyDetailsService)

    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build()

    def "should get policy details when claim number is valid"() {
        given:
        def policyDetailsDTO = PolicyDetailsDTO.builder()
                .claimNumber(12345678)
                .policyHolderName("mock-policy-holder-name")
                .coverageName("mock-coverage-name")
                .build()

        when:
        mockMvc.perform(
                get("/policy-details/by-claim-number/12345678")
                        .accept("application/json")
        )
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().json("{\"claimNumber\":12345678,\"policyHolderName\":\"mock-policy-holder-name\",\"policyNumber\":0,\"coverageName\":\"mock-coverage-name\",\"coverageLimit\":0,\"deductible\":0}"))
        then:
        1 * mockPolicyDetailsService.getPolicyDetails(12345678) >> policyDetailsDTO
    }
}
