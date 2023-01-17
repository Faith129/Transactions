package com.neptunesoftware.nipintegrator.inward.repository.database_results;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Builder
@Data
public class NIPAmountUnblock {
    private String sessionId;
    private String destinationInstitutionCode;
    private  int channelCode;
    private String referenceCode;
    private String targetAccountName;
    private String targetAccountNumber;
    private String targetBVN;
    private String reasonCode;
    private String narration;
    private String blockStatus;
    private BigDecimal amount;
    private BigDecimal balanceAmount;
    private String responseCode;
    private Timestamp createDate;


}
