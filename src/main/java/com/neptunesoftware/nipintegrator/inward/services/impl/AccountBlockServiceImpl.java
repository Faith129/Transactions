package com.neptunesoftware.nipintegrator.inward.services.impl;

import com.neptunesoftware.nipintegrator.NIP.constants.NIPResponseCodes;
import com.neptunesoftware.nipintegrator.inward.model.accountblock.AccountBlockRequest;
import com.neptunesoftware.nipintegrator.inward.model.accountblock.AccountBlockResponse;
import com.neptunesoftware.nipintegrator.inward.repository.AccountBlockOps;
import com.neptunesoftware.nipintegrator.inward.services.AccountBlockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountBlockServiceImpl implements AccountBlockService {

    private final AccountBlockOps repository;

    @Override
    public AccountBlockResponse accountBlock(AccountBlockRequest request) {
        String responseCode = NIPResponseCodes.UNSUCCESSFUL_ACCOUNT_OR_AMOUNT_BLOCK;

        if(!repository.isReferenceCodeUnique(request.getReferenceCode())) {
            responseCode = repository.performAccountBlock(request);

            //save
            if(!repository.saveAccountBlock(request, responseCode)) {
                responseCode = NIPResponseCodes.SYSTEM_MALFUNCTION;
            }
        }
        // create response object
        AccountBlockResponse response = new AccountBlockResponse();
        response.setSessionId(request.getSessionId());
        response.setDestinationInstitutionCode(request.getDestinationInstitutionCode());
        response.setChannelCode(request.getChannelCode());
        response.setReferenceCode(request.getReferenceCode());
        response.setTargetAccountNumber(request.getTargetAccountNumber());
        response.setTargetAccountName(request.getTargetAccountName());
        response.setTargetBankVerificationNumber(request.getTargetBankVerificationNumber());
        response.setReasonCode(request.getReasonCode());
        response.setNarration(request.getNarration());
        response.setResponseCode(responseCode);

        return response;
    }
}
