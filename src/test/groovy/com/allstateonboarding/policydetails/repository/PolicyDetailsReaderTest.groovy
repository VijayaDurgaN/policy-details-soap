package com.allstateonboarding.policydetails.repository

import com.allstateonboarding.policydetails.generated.PolicyDetails
import spock.lang.Specification

class PolicyDetailsReaderTest extends Specification {

    def "should read policy details when resource is valid"() {
        given:
        def reader = new PolicyDetailsReader("test_policy_details.json")
        when:
        def list = reader.readPolicyDetails()
        then:
        PolicyDetails policyDetails = new PolicyDetails()
        policyDetails.setClaimNumber(1233)
        policyDetails.setCoverageLimit(10000)
        policyDetails.setCoverageName("Durga")
        policyDetails.setPolicyHolderName("Vijaya")
        policyDetails.setDeductible(500)
        policyDetails.setPolicyNumber(123456)
        def details = list.get(0)
        details.claimNumber == policyDetails.claimNumber
        details.coverageLimit == policyDetails.coverageLimit
        details.coverageName == policyDetails.coverageName
        details.policyHolderName == policyDetails.policyHolderName
        details.policyNumber == policyDetails.policyNumber
        details.deductible == policyDetails.deductible
    }
}
