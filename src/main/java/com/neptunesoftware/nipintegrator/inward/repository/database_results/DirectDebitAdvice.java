package com.neptunesoftware.nipintegrator.inward.repository.database_results;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class DirectDebitAdvice {
    private String sessionId;
    private String nameEnquiryReference;
    private String destinationReferenceCode;
    private int channelCode;
    private String beneficiaryAccountName;
    private String beneficiaryAccountNumber;
    private String beneficiaryBankVerificationNumber;
    private String beneficiaryKYCLevel;
    private String debitAccountName;
    private String debitAccountNumber;
    private String debitBVN;
    private String debitKYCLevel;
    private String transactionLocation;
    private String narration;
    private String paymentReference;
    private String mandateReferenceNumber;
    private String transactionFee;
    private BigDecimal amount;
    private String responseCode;
    private String isReversed;
}
