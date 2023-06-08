package com.allstateonboarding.policydetailssoap.service;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class PolicyDetailsJmsDTO implements Serializable {
    private int claimNumber;
    private String policyHolderName;
    private int policyNumber;
    private String coverageName;
    private int coverageLimit;
    private int deductible;
}
