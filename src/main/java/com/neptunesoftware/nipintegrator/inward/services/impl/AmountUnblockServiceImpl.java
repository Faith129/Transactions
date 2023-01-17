package com.neptunesoftware.nipintegrator.inward.services.impl;

import com.neptunesoftware.nipintegrator.inward.model.amountunblock.AmountUnblockRequest;
import com.neptunesoftware.nipintegrator.inward.model.amountunblock.AmountUnblockResponse;
import com.neptunesoftware.nipintegrator.inward.repository.AmountUnblockOps;
import com.neptunesoftware.nipintegrator.inward.services.AmountUnblockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.neptunesoftware.nipintegrator.NIP.constants.NIPResponseCodes.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class AmountUnblockServiceImpl implements AmountUnblockService {

    private final AmountUnblockOps amountUnblockOps;

    @Override
    public AmountUnblockResponse doAmountUnblock(AmountUnblockRequest request) {
        log.info("======Starting amount unblock Operation!========");

        AmountUnblockResponse response = new AmountUnblockResponse();
        String responseCode = UNSUCCESSFUL_ACCOUNT_OR_AMOUNT_UNBLOCK;

        if (amountUnblockOps.isReferenceCodeValid(request.getReferenceCode())) {

            BigDecimal requestUnblockAmount = request.getAmount();
            BigDecimal blockedAmount = amountUnblockOps.getBlockedAmount(request.getReferenceCode(),
                    request.getTargetAccountNumber());

            responseCode = compareAmountInRequest(request, requestUnblockAmount, blockedAmount);
        }

        buildResponse(request, response, responseCode);
        return response;
    }

    private String compareAmountInRequest(AmountUnblockRequest request, BigDecimal requestUnblockAmount, BigDecimal blockedAmount) {
        String responseCode;
        boolean isUpdated;

        switch (requestUnblockAmount.compareTo(blockedAmount)) {
            case -1:

                responseCode = amountUnblockOps.doAmountUnBlock(request);
                if (responseCode.equals(APPROVED_OR_COMPLETED_SUCCESSFULLY)) {
                    BigDecimal amountLeft = blockedAmount.subtract(requestUnblockAmount);
                  isUpdated  = amountUnblockOps.partialAmountUnblock(request, amountLeft) == 0;
                    responseCode = isUpdated ? APPROVED_OR_COMPLETED_SUCCESSFULLY : SYSTEM_MALFUNCTION;
                }
                break;

            case 0:

                responseCode = amountUnblockOps.doAmountUnBlock(request);
                if (responseCode.equals(APPROVED_OR_COMPLETED_SUCCESSFULLY)){
                    isUpdated = amountUnblockOps.fullAmountUnblock(request) == 0;
                    responseCode = isUpdated ? APPROVED_OR_COMPLETED_SUCCESSFULLY : SYSTEM_MALFUNCTION;
                }
                break;

            case 1:
            default:
                responseCode = UNSUCCESSFUL_ACCOUNT_OR_AMOUNT_BLOCK;

        }
        return responseCode;
    }

    private void buildResponse(AmountUnblockRequest request, AmountUnblockResponse response, String responseCode) {

        response.setChannelCode(request.getChannelCode());
        response.setNarration(request.getNarration());
        response.setReferenceCode(request.getReferenceCode());
        response.setSessionId(request.getSessionId());
        response.setResponseCode(responseCode);
        response.setReasonCode(request.getReasonCode());
        response.setDestinationInstitutionCode(request.getDestinationInstitutionCode());
        response.setTargetAccountNumber(request.getTargetAccountNumber());
        response.setTargetBankVerificationNumber(request.getTargetBankVerificationNumber());
    }
}
