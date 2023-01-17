package com.neptunesoftware.nipintegrator.inward.repository;

import com.neptunesoftware.nipintegrator.inward.model.fundtransferdirectcredit.FundTransferDirectCreditRequest;
import com.neptunesoftware.nipintegrator.inward.model.fundtransferdirectcredit.FundTransferDirectCreditResponse;

public interface FTDirectCreditOps {

    boolean isAccountBlocked(String accountNumber);

}
