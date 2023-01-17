package com.neptunesoftware.nipintegrator.inward.repository;

import com.neptunesoftware.nipintegrator.inward.repository.database_results.BalanceEnquiry;

import java.util.List;

public interface BalanceEnquiryOps {
    boolean isAuthorizationValid(final String authCode);
    List<BalanceEnquiry> queryAccountBalance(final String accountNumber);
}
