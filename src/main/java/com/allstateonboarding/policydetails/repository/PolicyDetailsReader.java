package com.allstateonboarding.policydetails.repository;

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

@Component
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

