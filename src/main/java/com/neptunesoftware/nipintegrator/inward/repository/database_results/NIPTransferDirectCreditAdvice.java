package com.neptunesoftware.nipintegrator.inward.repository.database_results;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class NIPTransferDirectCreditAdvice {
    private String sessionId;
    private String nameEnquiryReference;
    private String destinationReferenceCode;
    private int channelCode;
    private String beneficiaryAccountName;
    private String beneficiaryAccountNumber;
    private String beneficiaryBankVerificationNumber;
    private String beneficiaryKYCLevel;
    private String originatorAccountName;
    private String originatorAccountNumber;
    private String originatorBVN;
    private String originatorKYCLevel;
    private String transaction_location;
    private String transactionLocation;
    private String narration;
    private String paymentReference;
    private BigDecimal amount;
    private String responseCode;
    private String isReversed;
    private String serviceType;

}
