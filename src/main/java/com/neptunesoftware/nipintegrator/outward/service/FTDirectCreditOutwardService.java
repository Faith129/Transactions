package com.neptunesoftware.nipintegrator.outward.service;

import com.neptunesoftware.nipintegrator.outward.api.NIPRequestType;
import com.neptunesoftware.nipintegrator.outward.api.fundtransferdirectcreditoutward.FundTransferDirectCreditRequest;
import com.neptunesoftware.nipintegrator.outward.api.fundtransferdirectcreditoutward.FundTransferDirectCreditResponse;

public interface FTDirectCreditOutwardService {

    FundTransferDirectCreditResponse performFundTransferDirectCredit(FundTransferDirectCreditRequest request);
    boolean savedFundTransfer(FundTransferDirectCreditResponse response, NIPRequestType inward);

}
