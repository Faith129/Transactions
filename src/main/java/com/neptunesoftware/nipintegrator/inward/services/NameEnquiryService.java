package com.neptunesoftware.nipintegrator.inward.services;

import com.neptunesoftware.nipintegrator.inward.model.nameenquiry.NESingleRequest;
import com.neptunesoftware.nipintegrator.inward.model.nameenquiry.NESingleResponse;

public interface NameEnquiryService {
    NESingleResponse getAccountName(final NESingleRequest request);
}
