package com.neptunesoftware.nipintegrator.inward.repository.database_results;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NIPAccount {
    private String sessionId;
    private String destinationInstitutionCode;
    private int channelCode;
    private String referenceCode;
    private String targetAccountName;
    private String targetAccountNumber;
    private String targetBVN;
    private String reasonCode;
    private String narration;
    private String lockStatus;
    private String responseCode;

}
