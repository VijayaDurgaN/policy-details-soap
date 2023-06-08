package com.allstateonboarding.policydetailsrest.client;

import com.allstateonboarding.policydetailsrest.generated.GetPolicyDetailsRequest;
import com.allstateonboarding.policydetailsrest.generated.GetPolicyDetailsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;


public class PolicyDetailsSoapClient extends WebServiceGatewaySupport {
    private final WebServiceTemplate webServiceTemplate = getWebServiceTemplate();
    @Value("${policy_details_soap_service_url}")
    private String policyDetailSoapServiceUrl;

    public GetPolicyDetailsResponse sendSoapRequest(GetPolicyDetailsRequest soapRequest) {
        return (GetPolicyDetailsResponse) webServiceTemplate.marshalSendAndReceive(
                policyDetailSoapServiceUrl,
                soapRequest
        );
    }
}
