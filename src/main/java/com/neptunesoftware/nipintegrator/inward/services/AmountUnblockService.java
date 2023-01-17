package com.neptunesoftware.nipintegrator.inward.services;

import com.neptunesoftware.nipintegrator.inward.model.amountunblock.AmountUnblockRequest;
import com.neptunesoftware.nipintegrator.inward.model.amountunblock.AmountUnblockResponse;

public interface AmountUnblockService {
    AmountUnblockResponse doAmountUnblock(AmountUnblockRequest request);
}
