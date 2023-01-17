package com.neptunesoftware.nipintegrator.outward.service.impl;

import com.neptunesoftware.nipintegrator.NIP.NIBSS;
import com.neptunesoftware.nipintegrator.inward.repository.NameEnquiryOps;
import com.neptunesoftware.nipintegrator.outward.api.nameenquiryoutward.NameEnquiryRequest;
import com.neptunesoftware.nipintegrator.outward.api.nameenquiryoutward.NameEnquiryResponse;
import com.neptunesoftware.nipintegrator.outward.service.NameEnquiryOutward;
import com.neptunesoftware.nipintegrator.utilities.Marshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NameEnquiryOutwardService implements NameEnquiryOutward {

    private final NIBSS nibss;

    @Override
    public NameEnquiryResponse performNameEnquiry(String destinationInstitutionCode, int channelCode, String accountNumber) {
        return nibss.nameEnquirySingleItem(destinationInstitutionCode, channelCode, accountNumber);
    }
}
