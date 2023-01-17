package com.neptunesoftware.nipintegrator.outward.repository;

import com.neptunesoftware.nipintegrator.outward.api.TransactionProperties;
import com.neptunesoftware.nipintegrator.outward.api.responsemodel.ResponseModel;
import com.neptunesoftware.nipintegrator.outward.api.tsq.TransactionStatusQueryResponse;

public interface TSQOutwardOps {
    ResponseModel postTransactions(TransactionProperties properties);
    boolean saveTSQ(TransactionStatusQueryResponse response);
}
