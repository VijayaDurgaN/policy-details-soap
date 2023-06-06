package com.allstateonboarding.policydetails.service;

import com.allstateonboarding.policydetails.exception.PolicyNotFoundException;
import com.allstateonboarding.policydetails.generated.GetPolicyDetailsResponse;
import com.allstateonboarding.policydetails.generated.PolicyDetails;
import com.allstateonboarding.policydetails.repository.PolicyDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PolicyDetailsService {

    private final PolicyDetailsRepository repository;

    @Autowired
    public PolicyDetailsService(PolicyDetailsRepository repository) {
        this.repository = repository;
    }

    public GetPolicyDetailsResponse getPolicyDetailsByClaimNumber(int claimNumber) {
        GetPolicyDetailsResponse getPolicyDetailsResponse = new GetPolicyDetailsResponse();
        PolicyDetails policyDetails = repository
                .findByClaimNumber(claimNumber)
                .orElseThrow(() -> new PolicyNotFoundException(
                        String.format("Policy with claim number %s not found", claimNumber)
                ));
        getPolicyDetailsResponse.setPolicyDetails(policyDetails);
        return getPolicyDetailsResponse;
    }
}
