package com.neptunesoftware.nipintegrator.inward.services;

import com.neptunesoftware.nipintegrator.inward.model.fundtransferadvicedirectdebit.FundTransferAdviceDirectDebitRequest;
import com.neptunesoftware.nipintegrator.inward.model.fundtransferadvicedirectdebit.FundTransferAdviceDirectDebitResponse;

public interface FundTransferAdviceDirectDebitService {
    FundTransferAdviceDirectDebitResponse getFundTransferAdviceDircetDebit (FundTransferAdviceDirectDebitRequest fundTransferAdviceDirectDebitRequest);
}
