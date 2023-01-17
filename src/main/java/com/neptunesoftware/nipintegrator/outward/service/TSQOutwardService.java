package com.neptunesoftware.nipintegrator.outward.service;

import com.neptunesoftware.nipintegrator.outward.api.NIPRequestType;
import com.neptunesoftware.nipintegrator.outward.api.TransactionProperties;
import com.neptunesoftware.nipintegrator.outward.api.fundtransferdirectcreditoutward.FundTransferDirectCreditResponse;
import com.neptunesoftware.nipintegrator.outward.api.responsemodel.ResponseModel;

public interface TSQOutwardService {

    String transactionStatusQuery(FundTransferDirectCreditResponse response, NIPRequestType requestType);
    ResponseModel postTransaction(TransactionProperties properties);
}
