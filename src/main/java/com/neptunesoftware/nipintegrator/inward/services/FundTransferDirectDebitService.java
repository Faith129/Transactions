package com.neptunesoftware.nipintegrator.inward.services;

import com.neptunesoftware.nipintegrator.inward.model.fundtransferdirectdebit.FundTransferDirectDebitRequest;
import com.neptunesoftware.nipintegrator.inward.model.fundtransferdirectdebit.FundTransferDirectDebitResponse;

public interface FundTransferDirectDebitService {
    FundTransferDirectDebitResponse doFundTransferDirectDebit(FundTransferDirectDebitRequest request);
}
