package com.neptunesoftware.nipintegrator.inward.services.impl;

import com.neptunesoftware.nipintegrator.NIP.constants.NIPResponseCodes;
import com.neptunesoftware.nipintegrator.inward.model.amountblock.AmountBlockRequest;
import com.neptunesoftware.nipintegrator.inward.model.amountblock.AmountBlockResponse;
import com.neptunesoftware.nipintegrator.inward.repository.AmountBlockOps;
import com.neptunesoftware.nipintegrator.inward.services.AmountBlockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.neptunesoftware.nipintegrator.NIP.constants.NIPResponseCodes.UNSUCCESSFUL_ACCOUNT_OR_AMOUNT_BLOCK;

@Service
@RequiredArgsConstructor
public class AmountBlockServiceImpl implements AmountBlockService {
    private final AmountBlockOps amountBlockOps;

    @Override
    public AmountBlockResponse getAmountBlock (AmountBlockRequest request){
        String responseCode = UNSUCCESSFUL_ACCOUNT_OR_AMOUNT_BLOCK;
        if(amountBlockOps.isReferenceCodeUnique(request.getReferenceCode())) {
            responseCode = amountBlockOps.performAmountBlock(request);
            if(!amountBlockOps.saveAmountBlock(request, responseCode)) {
                responseCode = NIPResponseCodes.SYSTEM_MALFUNCTION;
            }
        }
        return buildResponse(request, responseCode);
    }

    private AmountBlockResponse buildResponse (AmountBlockRequest request, String responseCode) {
        AmountBlockResponse response = new AmountBlockResponse();
        response.setSessionId(request.getSessionId());
        response.setDestinationInstitutionCode(request.getDestinationInstitutionCode());
        response.setChannelCode(request.getChannelCode());
        response.setReferenceCode(request.getReferenceCode());
        response.setTargetAccountName(request.getTargetAccountName());
        response.setTargetBankVerificationNumber(request.getTargetBankVerificationNumber());
        response.setTargetAccountNumber(request.getTargetAccountNumber());
        response.setResponseCode(request.getReasonCode());
        response.setNarration(request.getNarration());
        response.setAmount(request.getAmount());
        response.setResponseCode(responseCode);

        return response;
    }
}
