package com.allstateonboarding.policydetailssoap.service;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class PolicyDetailsJmsDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -295422703255886286L;

    private int claimNumber;
    private String policyHolderName;
    private int policyNumber;
    private String coverageName;
    private int coverageLimit;
    private int deductible;
}
