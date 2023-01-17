package com.neptunesoftware.nipintegrator.inward.services.impl;

import com.neptunesoftware.nipintegrator.NIP.constants.NIPResponseCodes;
import com.neptunesoftware.nipintegrator.inward.model.nameenquiry.NESingleRequest;
import com.neptunesoftware.nipintegrator.inward.model.nameenquiry.NESingleResponse;
import com.neptunesoftware.nipintegrator.inward.repository.database_results.NameEnquiry;
import com.neptunesoftware.nipintegrator.inward.repository.NameEnquiryOps;
import com.neptunesoftware.nipintegrator.inward.services.NameEnquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NameEnquiryServiceImpl implements NameEnquiryService {
    private final NameEnquiryOps nameEnquiryOps;

    @Override
    public NESingleResponse getAccountName(NESingleRequest request) {
      NameEnquiry enquiry = nameEnquiryOps
              .queryAccountName(request.getAccountNumber()).get(0);

      NESingleResponse response = new NESingleResponse();;
      response.setAccountName(enquiry.getTitle().toUpperCase());
      response.setSessionId(request.getSessionId());
      response.setAccountNumber(request.getAccountNumber());
      response.setResponseCode(NIPResponseCodes.APPROVED_OR_COMPLETED_SUCCESSFULLY);
      response.setKycLevel("1");
      response.setBankVerificationNumber(enquiry.getBvn());
      return response;
    }

}
