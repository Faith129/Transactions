package com.neptunesoftware.nipintegrator.outward.service;

import com.neptunesoftware.nipintegrator.outward.api.nameenquiryoutward.NameEnquiryRequest;
import com.neptunesoftware.nipintegrator.outward.api.nameenquiryoutward.NameEnquiryResponse;

public interface NameEnquiryOutward {
    NameEnquiryResponse performNameEnquiry(String destinationInstitutionCode, int channelCode, String accountNumber);
}
