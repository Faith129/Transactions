package com.neptunesoftware.nipintegrator.inward.repository.database_results;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Builder
@Data
public class MandateAdvice {
    private String sessionId;
    private String destinationInstitutionCode;
    private int channelCode;
    private String mandateReferenceNumber;
    private BigDecimal amount;
    private String debitAccountName;
    private String debitAccountNumber;
    private String debitAccountBVN;
    private int debKycLevel;
    private String beneficiaryAccountName;
    private String beneficiaryAccountNumber;
    private String beneficiaryAccountBVN;
    private int benKycLevel;
    private String responseCode;
    private Timestamp createDate;


}
