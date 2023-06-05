package com.allstateonboarding.policydetails.repo;

import com.allstateonboarding.policydetails.exception.InternalServerError;
import com.allstateonboarding.policydetails.generated.PolicyDetails;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

//@Repository
@Component
public class PolicyDetailsRepository {

    public Optional<PolicyDetails> findByClaimNumber(int claimNumber) {
        try (InputStream stream = new ClassPathResource("mockPolicyDetails.json").getInputStream()) {

            String policyDetailsJson = IOUtils.toString(stream, Charset.defaultCharset());
            ObjectMapper objectMapper = new ObjectMapper();
            List<PolicyDetails> policyDetails = objectMapper.readValue(
                    policyDetailsJson,
                    new TypeReference<>() {
                    }
            );

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
