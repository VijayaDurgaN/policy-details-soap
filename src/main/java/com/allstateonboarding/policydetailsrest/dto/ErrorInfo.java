package com.allstateonboarding.policydetailsrest.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorInfo {
    private String code;
    private String message;
    private LocalDateTime timestamp;
}
