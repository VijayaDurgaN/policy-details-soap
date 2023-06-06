package com.allstateonboarding.policydetailsrest.client;

import com.allstateonboarding.policydetailsrest.generated.GetPolicyDetailsRequest;
import com.allstateonboarding.policydetailsrest.generated.GetPolicyDetailsResponse;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;


public class SoapClient extends WebServiceGatewaySupport {
    private final WebServiceTemplate webServiceTemplate = getWebServiceTemplate();

    public GetPolicyDetailsResponse sendSoapRequest(GetPolicyDetailsRequest soapRequest) {

        return (GetPolicyDetailsResponse) webServiceTemplate.marshalSendAndReceive("http://localhost:8080/ws/policy-details", soapRequest);
    }
}
