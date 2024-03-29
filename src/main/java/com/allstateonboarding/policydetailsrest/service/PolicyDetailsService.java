package com.allstateonboarding.policydetailsrest.service;

import com.allstateonboarding.policydetailsrest.client.PolicyDetailsSoapClient;
import com.allstateonboarding.policydetailsrest.dto.PolicyDetailsDTO;
import com.allstateonboarding.policydetailsrest.exception.BadRequestException;
import com.allstateonboarding.policydetailsrest.exception.PolicyNotFoundException;
import com.allstateonboarding.policydetailsrest.exception.ServiceUnavailableException;
import com.allstateonboarding.policydetailsrest.generated.GetPolicyDetailsRequest;
import com.allstateonboarding.policydetailsrest.generated.PolicyDetails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.WebServiceIOException;
import org.springframework.ws.soap.client.SoapFaultClientException;

@Service
public class PolicyDetailsService {
    private final PolicyDetailsSoapClient soapClient;

    @Autowired
    public PolicyDetailsService(PolicyDetailsSoapClient soapClient) {
        this.soapClient = soapClient;
    }

    private static final String POLICY_NOT_FOUND_EXCEPTION_FAULT_CODE = "POLICY_NOT_FOUND_EXCEPTION";
    private static final String INTERNAL_SERVER_ERROR_FAULT_CODE = "INTERNAL_SERVER_ERROR";

    public PolicyDetailsDTO getPolicyDetails(Integer claimNumber) {
        GetPolicyDetailsRequest getPolicyDetailsRequest = new GetPolicyDetailsRequest();
        getPolicyDetailsRequest.setClaimNumber(claimNumber);

        try {
            PolicyDetails policyDetails = soapClient
                    .sendSoapRequest(getPolicyDetailsRequest)
                    .getPolicyDetails();
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(policyDetails, PolicyDetailsDTO.class);
        } catch (WebServiceIOException e) {
            throw new ServiceUnavailableException("Service unavailable");
        } catch (SoapFaultClientException e) {
            String localPart = e.getFaultCode().getLocalPart();
            String faultStringOrReason = e.getFaultStringOrReason();

            if (localPart.equals(POLICY_NOT_FOUND_EXCEPTION_FAULT_CODE)) {
                throw new PolicyNotFoundException(faultStringOrReason);
            } else if (localPart.equals(INTERNAL_SERVER_ERROR_FAULT_CODE)) {
                throw new ServiceUnavailableException(faultStringOrReason);
            }
            throw new BadRequestException("Invalid soap request : " + faultStringOrReason);
        }
    }
}
