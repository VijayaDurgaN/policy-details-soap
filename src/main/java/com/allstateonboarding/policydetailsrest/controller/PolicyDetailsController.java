package com.allstateonboarding.policydetailsrest.controller;

import com.allstateonboarding.policydetailsrest.dto.PolicyDetailsDTO;
import com.allstateonboarding.policydetailsrest.service.PolicyDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/policy-details")
public class PolicyDetailsController {

    private final PolicyDetailsService service;

    @Autowired
    public PolicyDetailsController(PolicyDetailsService policyDetailsService) {
        this.service = policyDetailsService;
    }

    @GetMapping("/by-claim-number/{claimNumber}")
    public PolicyDetailsDTO getPolicyDetails(@PathVariable Integer claimNumber) {
        return service.getPolicyDetails(claimNumber);
    }
}
