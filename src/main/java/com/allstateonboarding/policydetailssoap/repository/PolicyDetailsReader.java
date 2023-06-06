package com.allstateonboarding.policydetailssoap.repository;

import com.allstateonboarding.policydetailssoap.generated.PolicyDetails;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

public class PolicyDetailsReader {
     private final String resource;

    public PolicyDetailsReader(String resource) {
        this.resource = resource;
    }

    public List<PolicyDetails> readPolicyDetails() throws IOException {
        InputStream stream = new ClassPathResource(resource).getInputStream();
        String policyDetailsJson = IOUtils.toString(stream, Charset.defaultCharset());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(
                policyDetailsJson,
                new TypeReference<>() {
                }
        );
    }
}

