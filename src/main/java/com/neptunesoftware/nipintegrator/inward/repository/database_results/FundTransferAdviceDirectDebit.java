package com.neptunesoftware.nipintegrator.inward.repository.database_results;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Builder
public class FundTransferAdviceDirectDebit {

    private String sessionId;

    private String nameEnquiryRef;

    private String destinationInstitutionCode;

    private int channelCode;

    private String debitAccountName;

    private String debitAccountNumber;

    private String debitBankVerificationNumber;

    private String debitKYCLevel;

    private String beneficiaryAccountName;

    private String beneficiaryAccountNumber;

    private String beneficiaryBankVerificationNumber;

    private String beneficiaryKYCLevel;

    private String transactionLocation;

    private String narration;

    private String paymentReference;

    private String mandateReferenceNumber;

    private BigDecimal transactionFee;

    private BigDecimal amount;
}
