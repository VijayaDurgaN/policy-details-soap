package com.allstateonboarding.policydetailssoap.endpoint;

import com.allstateonboarding.policydetailssoap.generated.GetPolicyDetailsRequest;
import com.allstateonboarding.policydetailssoap.generated.GetPolicyDetailsResponse;
import com.allstateonboarding.policydetailssoap.service.PolicyDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class PolicyDetailsEndpoint {
    private static final String NAMESPACE = "http://allstate.com/policy";

    private final PolicyDetailsService service;

    @Autowired
    public PolicyDetailsEndpoint(PolicyDetailsService service) {
        this.service = service;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "GetPolicyDetailsRequest")
    @ResponsePayload
    public GetPolicyDetailsResponse getPolicyDetailsResponse(@RequestPayload @Valid GetPolicyDetailsRequest getPolicyDetailsRequest) {
        return service.getPolicyDetailsByClaimNumber(getPolicyDetailsRequest.getClaimNumber());
    }
}
