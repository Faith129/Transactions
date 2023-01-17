package com.neptunesoftware.nipintegrator.inward.repository;

import com.neptunesoftware.nipintegrator.inward.model.fundtransferdirectdebit.FundTransferDirectDebitResponse;

import java.math.BigDecimal;

public interface FundTransferDirectDebitOps {
    void saveIntoFundTransferDirectDebit(FundTransferDirectDebitResponse response);
    boolean isAccountBlocked(String debitAccountNumber);
    BigDecimal getMandateAmount(String mandateReferenceNumber);

    boolean isFundTransferDirectDebitSaved(FundTransferDirectDebitResponse response);
}
