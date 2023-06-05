package com.allstateonboarding.policydetails.endpoint;

import com.allstateonboarding.policydetails.generated.GetPolicyDetailsRequest;
import com.allstateonboarding.policydetails.generated.GetPolicyDetailsResponse;
import com.allstateonboarding.policydetails.generated.PolicyDetails;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class PolicyDetailsEndpoint {
    private static final String NAMESPACE = "http://allstate.com/policy";

    @PayloadRoot(namespace = NAMESPACE, localPart = "GetPolicyDetailsRequest")
    @ResponsePayload
    public GetPolicyDetailsResponse getPolicyDetailsResponse(@RequestPayload GetPolicyDetailsRequest getPolicyDetailsRequest) {
        GetPolicyDetailsResponse response = new GetPolicyDetailsResponse();

        PolicyDetails policyDetails = new PolicyDetails();
        policyDetails.setClaimNumber(getPolicyDetailsRequest.getClaimNumber());
        policyDetails.setPolicyHolderName("Policy Holder Name");
        policyDetails.setPolicyNumber(12345678);
        policyDetails.setCoverageName("Coverage Name");
        policyDetails.setCoverageLimit(200000);
        policyDetails.setDeductible(1000);

        response.setPolicyDetails(policyDetails);
        return response;
    }
}
