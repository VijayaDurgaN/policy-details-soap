package com.allstateonboarding.policydetailsrest.service;

import com.allstateonboarding.policydetailsrest.client.SoapClient;
import com.allstateonboarding.policydetailsrest.dto.PolicyDetailsDTO;
import com.allstateonboarding.policydetailsrest.generated.GetPolicyDetailsRequest;
import com.allstateonboarding.policydetailsrest.generated.PolicyDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PolicyDetailsService {

    private final SoapClient soapClient;

    @Autowired
    public PolicyDetailsService(SoapClient soapClient) {
        this.soapClient = soapClient;
    }

    public PolicyDetailsDTO getPolicyDetails(Integer claimNumber) {
        GetPolicyDetailsRequest getPolicyDetailsRequest = new GetPolicyDetailsRequest();
        getPolicyDetailsRequest.setClaimNumber(claimNumber);

        PolicyDetails policyDetails = soapClient
                .sendSoapRequest(getPolicyDetailsRequest)
                .getPolicyDetails();

        return PolicyDetailsDTO.builder()
                .policyNumber(policyDetails.getPolicyNumber())
                .claimNumber(policyDetails.getClaimNumber())
                .coverageName(policyDetails.getCoverageName())
                .coverageLimit(policyDetails.getCoverageLimit())
                .policyHolderName(policyDetails.getPolicyHolderName())
                .deductible(policyDetails.getDeductible())
                .build();
    }
}
