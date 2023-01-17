package com.neptunesoftware.nipintegrator.inward.services;

import com.neptunesoftware.nipintegrator.inward.model.fundtransferdirectcredit.FundTransferDirectCreditRequest;
import com.neptunesoftware.nipintegrator.outward.api.fundtransferdirectcreditoutward.FundTransferDirectCreditResponse;

public interface FTDirectCreditService {
    FundTransferDirectCreditResponse executeFundTransfer(FundTransferDirectCreditRequest request);
}
