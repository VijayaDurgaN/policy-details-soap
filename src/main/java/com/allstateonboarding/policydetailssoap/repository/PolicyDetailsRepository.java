package com.allstateonboarding.policydetailssoap.repository;

import com.allstateonboarding.policydetailssoap.exception.InternalServerError;
import com.allstateonboarding.policydetailssoap.exception.PolicyNotFoundException;
import com.allstateonboarding.policydetailssoap.generated.PolicyDetails;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public class PolicyDetailsRepository {

    private final PolicyDetailsReader reader;
    private Logger logger = org.slf4j.LoggerFactory.getLogger(PolicyDetailsRepository.class);

    @Autowired
    public PolicyDetailsRepository(PolicyDetailsReader reader) {
        this.reader = reader;
    }

    public PolicyDetails findByClaimNumber(int claimNumber) {
        try {
            List<PolicyDetails> policyDetails = reader.readPolicyDetails();
            return policyDetails
                    .stream()
                    .filter(policy -> policy.getClaimNumber() == claimNumber)
                    .findFirst()
                    .orElseThrow(() -> {
                        String errorMessage = String.format("Policy with claim number %s not found", claimNumber);
                        logger.error(errorMessage);
                        return new PolicyNotFoundException(errorMessage);
                    });
        } catch (IOException e) {
            String errorMessage = "Error fetching policy details";
            logger.error(errorMessage);
            throw new InternalServerError(errorMessage);
        }
    }
}
