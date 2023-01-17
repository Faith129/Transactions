package com.neptunesoftware.nipintegrator.inward.repository;

import com.neptunesoftware.nipintegrator.inward.repository.database_results.NIPTransferDirectCreditAdvice;

import java.util.List;

public interface FTAdviceDirectCreditOps {
    List<NIPTransferDirectCreditAdvice> performFundTransferCreditAdvice(String id);
    boolean isSessionIDValid(String sessionID);
    boolean isTXNReversedAlready(String sessionID);
    void updateFundReversalStatus(String sessionID);

}
