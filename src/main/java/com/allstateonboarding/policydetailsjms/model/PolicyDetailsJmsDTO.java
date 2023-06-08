package com.allstateonboarding.policydetailsjms.model;

import java.io.Serializable;

@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.Getter
@lombok.Setter
@lombok.ToString
@lombok.Generated
public class PolicyDetailsJmsDTO implements Serializable {
    private int claimNumber;
    private String policyHolderName;
    private int policyNumber;
    private String coverageName;
    private int coverageLimit;
    private int deductible;
}
