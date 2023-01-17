package com.neptunesoftware.nipintegrator.inward.repository;

import com.neptunesoftware.nipintegrator.inward.model.fundtransferadvicedirectdebit.FundTransferAdviceDirectDebitRequest;

import java.math.BigDecimal;

public interface FundTransferAdviceDirectDebitOps {
    String performFundTransferDebitAdvice(FundTransferAdviceDirectDebitRequest fundTransferAdviceDirectDebitRequest);
    boolean isSessionIDValid(String sessionID);
    boolean isTxnReversedAlready(String sessionID);
    boolean updateFundTansferReversalStatus(String sessionID);
    BigDecimal getMandateAmount(String mandateReference);

}
