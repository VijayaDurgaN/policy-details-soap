package com.allstateonboarding.policydetailsrest.controller

import com.allstateonboarding.policydetailsrest.dto.PolicyDetailsDTO
import com.allstateonboarding.policydetailsrest.exception.BadRequestException
import com.allstateonboarding.policydetailsrest.exception.PolicyNotFoundException
import com.allstateonboarding.policydetailsrest.exception.ServiceUnavailableException
import com.allstateonboarding.policydetailsrest.service.PolicyDetailsService
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class PolicyDetailsControllerTest extends Specification {

    def mockPolicyDetailsService = Mock(PolicyDetailsService)
    def controller = new PolicyDetailsController(mockPolicyDetailsService)

    MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(controller)
            .setControllerAdvice(new PolicyDetailsControllerAdvice())
            .build()

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

    def "body should represent service error when service is unavailable"() {
        1 * mockPolicyDetailsService.getPolicyDetails(12345678) >> {
            throw new ServiceUnavailableException("Service unavailable")
        }
        expect:
        mockMvc.perform(
                get("/policy-details/by-claim-number/12345678")
                        .accept("application/json")
        )
                .andExpect(status().isInternalServerError())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("\$.code").value("SERVICE_UNAVAILABLE"))
                .andExpect(jsonPath("\$.message").value("Service unavailable"))
    }

    def "body should represent policy not found error when policy with claim number does not exist"() {
        1 * mockPolicyDetailsService.getPolicyDetails(12345678) >> {
            throw new PolicyNotFoundException("Policy not found")
        }
        expect:
        mockMvc.perform(
                get("/policy-details/by-claim-number/12345678")
                        .accept("application/json")
        )
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("\$.code").value("POLICY_NOT_FOUND"))
                .andExpect(jsonPath("\$.message").value("Policy not found"))
    }

    def "body should represent bad request error when claim number is invalid"() {
        1 * mockPolicyDetailsService.getPolicyDetails(12345) >> {
            throw new BadRequestException("Claim number invalid")
        }
        expect:
        mockMvc.perform(
                get("/policy-details/by-claim-number/12345")
                        .accept("application/json")
        )
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("\$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("\$.message").value("Claim number invalid"))
    }
}
