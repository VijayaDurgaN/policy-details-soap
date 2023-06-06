package com.allstateonboarding.policydetailsrest.controller;

import com.allstateonboarding.policydetailsrest.client.SoapClient;
import com.allstateonboarding.policydetailsrest.generated.GetPolicyDetailsRequest;
import com.allstateonboarding.policydetailsrest.generated.GetPolicyDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/policy-details")
public class PolicyDetailsController {

    @Autowired
    SoapClient soapClient;

    @GetMapping("/by-claim-number/{claimNumber}")
    public GetPolicyDetailsResponse getPolicyDetails(@PathVariable Integer claimNumber) {
        GetPolicyDetailsRequest getPolicyDetailsRequest = new GetPolicyDetailsRequest();
        getPolicyDetailsRequest.setClaimNumber(claimNumber);
        GetPolicyDetailsResponse getPolicyDetailsResponse = soapClient.sendSoapRequest(getPolicyDetailsRequest);
        return getPolicyDetailsResponse;
    }
}
