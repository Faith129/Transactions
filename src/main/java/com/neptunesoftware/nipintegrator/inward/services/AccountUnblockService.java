package com.neptunesoftware.nipintegrator.inward.services;

import com.neptunesoftware.nipintegrator.inward.model.accountunblock.AccountUnblockRequest;
import com.neptunesoftware.nipintegrator.inward.model.accountunblock.AccountUnblockResponse;

public interface AccountUnblockService {
    AccountUnblockResponse unblockAccount(AccountUnblockRequest request);
}
