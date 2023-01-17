package com.neptunesoftware.nipintegrator.inward.services;

import com.neptunesoftware.nipintegrator.inward.model.balanceenquiry.BalanceEnquiryResponse;

public interface BalanceEnquiryService {
    BalanceEnquiryResponse getBalance(final String accountNumber);
}
