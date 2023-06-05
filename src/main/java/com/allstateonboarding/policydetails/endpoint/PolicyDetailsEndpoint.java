package com.allstateonboarding.policydetails.endpoint;

import com.allstateonboarding.policydetails.generated.GetPolicyDetailsRequest;
import com.allstateonboarding.policydetails.generated.GetPolicyDetailsResponse;
import com.allstateonboarding.policydetails.service.PolicyDetailsService;
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
    public GetPolicyDetailsResponse getPolicyDetailsResponse(@RequestPayload GetPolicyDetailsRequest getPolicyDetailsRequest) {
        return service.getPolicyDetailsByClaimNumber(getPolicyDetailsRequest.getClaimNumber());
    }
}
