package com.allstateonboarding.policydetails.service;

import com.allstateonboarding.policydetails.exception.PolicyNotFoundException;
import com.allstateonboarding.policydetails.generated.GetPolicyDetailsResponse;
import com.allstateonboarding.policydetails.generated.PolicyDetails;
import com.allstateonboarding.policydetails.repository.PolicyDetailsRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PolicyDetailsService {

    private final PolicyDetailsRepository repository;
    protected Logger logger = org.slf4j.LoggerFactory.getLogger(PolicyDetailsService.class);


    @Autowired
    public PolicyDetailsService(PolicyDetailsRepository repository) {
        this.repository = repository;
    }

    public GetPolicyDetailsResponse getPolicyDetailsByClaimNumber(int claimNumber) {
        logger.info("Fetching policy details for claim number {}", claimNumber);
        GetPolicyDetailsResponse getPolicyDetailsResponse = new GetPolicyDetailsResponse();
        PolicyDetails policyDetails = repository
                .findByClaimNumber(claimNumber)
                .orElseThrow(() -> {
                    String errorMessage = String.format("Policy with claim number %s not found", claimNumber);
                    logger.error(errorMessage);
                    return new PolicyNotFoundException(errorMessage);
                });
        getPolicyDetailsResponse.setPolicyDetails(policyDetails);
        logger.info("Fetching policy details for claim number {} completed", claimNumber);
        return getPolicyDetailsResponse;
    }
}
