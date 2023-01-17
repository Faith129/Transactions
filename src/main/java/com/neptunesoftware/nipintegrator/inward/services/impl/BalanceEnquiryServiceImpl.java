package com.neptunesoftware.nipintegrator.inward.services.impl;

import com.neptunesoftware.nipintegrator.inward.model.balanceenquiry.BalanceEnquiryResponse;
import com.neptunesoftware.nipintegrator.inward.repository.BalanceEnquiryOps;
import com.neptunesoftware.nipintegrator.inward.repository.database_results.BalanceEnquiry;
import com.neptunesoftware.nipintegrator.inward.services.BalanceEnquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceEnquiryServiceImpl implements BalanceEnquiryService {

    private final BalanceEnquiryOps balanceEnquiryOps;
    @Override
    public BalanceEnquiryResponse getBalance(String accountNumber) {


        BalanceEnquiry enquiry = balanceEnquiryOps
                .queryAccountBalance(
                       accountNumber
                ).get(0);


        BalanceEnquiryResponse response = new BalanceEnquiryResponse();

        response.setAvailableBalance(enquiry.getAccountBalance());
        response.setTargetAccountNumber(accountNumber);

        return response;
    }
}
