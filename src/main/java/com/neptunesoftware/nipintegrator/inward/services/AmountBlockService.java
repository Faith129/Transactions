package com.neptunesoftware.nipintegrator.inward.services;

import com.neptunesoftware.nipintegrator.inward.model.amountblock.AmountBlockRequest;
import com.neptunesoftware.nipintegrator.inward.model.amountblock.AmountBlockResponse;

public interface AmountBlockService {

    AmountBlockResponse getAmountBlock (AmountBlockRequest request);
}
