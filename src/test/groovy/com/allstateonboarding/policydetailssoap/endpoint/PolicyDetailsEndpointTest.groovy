package com.allstateonboarding.policydetailssoap.endpoint


import com.allstateonboarding.policydetailssoap.service.PolicyDetailsJmsDTO
import jakarta.jms.ConnectionFactory
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory
import org.assertj.core.api.Assertions
import org.junit.ClassRule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.webservices.server.AutoConfigureMockWebServiceClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.config.DefaultJmsListenerContainerFactory
import org.springframework.jms.core.JmsTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.ws.test.server.MockWebServiceClient
import org.springframework.xml.transform.StringSource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.spock.Testcontainers
import org.testcontainers.utility.DockerImageName
import spock.lang.Specification

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.springframework.ws.test.server.RequestCreators.withPayload
import static org.springframework.ws.test.server.ResponseMatchers.*

@SpringBootTest
@AutoConfigureMockWebServiceClient
//@Testcontainers
class PolicyDetailsEndpointTest extends Specification {

    @Autowired
    private MockWebServiceClient client
//
//    @ClassRule
//    public static activeMqContainer = new GenericContainer<>(
//            DockerImageName.parse("rmohr/activemq:5.14.3")
//    ).withExposedPorts(61616)
//
//    static {
//        activeMqContainer.start();
//    }
//
//    @Autowired
//    private JmsTemplate template


    def "should get PolicyDetails when claim number is valid"() {
        expect:

        StringSource request = new StringSource(
                "<ns:GetPolicyDetailsRequest xmlns:ns='http://allstate.com/policy'>" +
                        "<ns:claimNumber>1233</ns:claimNumber>" +
                        "</ns:GetPolicyDetailsRequest>"
        )
        StringSource response = new StringSource(
                "<ns:GetPolicyDetailsResponse xmlns:ns='http://allstate.com/policy'>" +
                        "<ns:PolicyDetails>" +
                        "<ns:claimNumber>1233</ns:claimNumber>" +
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

//        def sentMessage = template.receiveAndConvert("policyDetailsQueue");
//        Assertions.assertThat(sentMessage)
//                .isInstanceOf(PolicyDetailsJmsDTO.class);
//
//        assertEquals(
//                PolicyDetailsJmsDTO.builder()
//                        .claimNumber(1233)
//                        .policyHolderName("Vijaya")
//                        .policyNumber(123456)
//                        .coverageName("Durga")
//                        .coverageLimit(10000)
//                        .deductible(500)
//                        .build(),
//                (PolicyDetailsJmsDTO) sentMessage
//        );
    }

//    @Configuration
//    @EnableJms
//    static class TestConfiguration {
//        @Bean
//        static jmsListenerContainerFactory() {
//            DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory()
//            factory.setConnectionFactory(connectionFactory())
//            return factory
//        }
//
//        @Bean
//        static ConnectionFactory connectionFactory() {
//            String brokerUrlFormat = "tcp://%s:%d"
//            String brokerUrl = String.format(
//                    brokerUrlFormat,
//                    activeMqContainer.host,
//                    activeMqContainer.firstMappedPort
//            )
//            return new ActiveMQConnectionFactory(brokerUrl) as ConnectionFactory
//        }
//
//        @Bean
//        static JmsTemplate jmsTemplate() {
//            return new JmsTemplate(connectionFactory())
//        }
//    }

}
