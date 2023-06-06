package com.allstateonboarding.policydetailsrest.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest
class PolicyDetailsControllerTest extends Specification {

    @Autowired
    MockMvc mockMvc

    def "should get policy details when claim number is valid"() {
        expect:
        mockMvc.perform(
                get("/policy-details/by-claim-number/12345678")
        ).andExpect(
                status().isOk()
        )
    }
}
