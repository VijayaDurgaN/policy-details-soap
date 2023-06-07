package com.allstateonboarding.policydetailsjms.model;


import com.allstateonboarding.policydetailssoap.service.PolicyDetailsJmsDTO;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PolicyDetailsResult implements Serializable {
    private PolicyDetailsJmsDTO policyDetails;
    private Boolean success;
    private String description;
}
