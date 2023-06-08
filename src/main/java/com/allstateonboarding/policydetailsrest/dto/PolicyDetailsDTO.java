package com.allstateonboarding.policydetailsrest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PolicyDetailsDTO {
    private int claimNumber;
    private String policyHolderName;
    private int policyNumber;
    private String coverageName;
    private int coverageLimit;
    private int deductible;
}
