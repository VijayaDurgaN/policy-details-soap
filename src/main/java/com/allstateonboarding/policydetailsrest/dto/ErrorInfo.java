package com.allstateonboarding.policydetailsrest.dto;


import lombok.Builder;
import lombok.Data;
import lombok.Generated;

import java.time.LocalDateTime;

@Data
@Builder
@Generated
public class ErrorInfo {
    private String code;
    private String message;
    private LocalDateTime timestamp;
}
