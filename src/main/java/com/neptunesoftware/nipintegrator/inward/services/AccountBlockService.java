package com.neptunesoftware.nipintegrator.inward.services;

import com.neptunesoftware.nipintegrator.inward.model.accountblock.AccountBlockResponse;
import com.neptunesoftware.nipintegrator.inward.model.accountblock.AccountBlockRequest;
public interface AccountBlockService {
     AccountBlockResponse accountBlock(AccountBlockRequest request);
}
