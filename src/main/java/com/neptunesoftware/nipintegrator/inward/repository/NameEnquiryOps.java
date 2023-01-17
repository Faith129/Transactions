package com.neptunesoftware.nipintegrator.inward.repository;

import com.neptunesoftware.nipintegrator.inward.repository.database_results.NameEnquiry;

import java.util.List;

public interface NameEnquiryOps {
    List<NameEnquiry> queryAccountName(final String accountNumber);
}
