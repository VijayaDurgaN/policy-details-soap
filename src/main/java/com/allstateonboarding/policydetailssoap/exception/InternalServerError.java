package com.allstateonboarding.policydetailssoap.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CUSTOM, customFaultCode = "{http://allstate.com/policy}INTERNAL_SERVER_ERROR")
public class InternalServerError extends RuntimeException {
    public InternalServerError(String message) {
        super(message);
    }
}
