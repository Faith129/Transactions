package com.neptunesoftware.nipintegrator.inward.repository.database_results;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
public class AmountBlock {
    private String sessionId;
    private String destinationInstitutionCode;
    private int channelCode;
    private String referenceCode;
    private String targetAccountName;
    private String targetAccountNumber;
    private String targetBankVerificationNumber;
    private String reasonCode;
    private String narration;
    private BigDecimal amount;
    private String blockStatus;
    private BigDecimal balanceAmount;
    private String responseCode;
    private Timestamp createDate;

}
