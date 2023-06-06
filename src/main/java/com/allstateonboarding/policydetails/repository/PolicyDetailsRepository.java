package com.allstateonboarding.policydetails.repository;

import com.allstateonboarding.policydetails.exception.InternalServerError;
import com.allstateonboarding.policydetails.generated.PolicyDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Repository
public class PolicyDetailsRepository {

    private final PolicyDetailsReader reader;

    @Autowired
    public PolicyDetailsRepository(PolicyDetailsReader reader) {
        this.reader = reader;
    }

    public Optional<PolicyDetails> findByClaimNumber(int claimNumber) {
        try {
            List<PolicyDetails> policyDetails = reader.readPolicyDetails();
            return policyDetails
                    .stream()
                    .filter(policy -> policy.getClaimNumber() == claimNumber)
                    .findFirst();
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalServerError("Error fetching policy details");
        }
    }
}
