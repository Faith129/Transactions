package com.neptunesoftware.nipintegrator.inward.services.impl;

import com.neptunesoftware.nipintegrator.inward.model.accountunblock.AccountUnblockRequest;
import com.neptunesoftware.nipintegrator.inward.model.accountunblock.AccountUnblockResponse;
import com.neptunesoftware.nipintegrator.inward.repository.AccountUnblockOps;
import com.neptunesoftware.nipintegrator.inward.services.AccountUnblockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.neptunesoftware.nipintegrator.NIP.constants.NIPResponseCodes.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountUnblockServiceImpl implements AccountUnblockService {

    private final AccountUnblockOps accountUnblockOps;

    @Override
    public AccountUnblockResponse unblockAccount(AccountUnblockRequest request) {

        System.out.println("========================================");
        System.out.println("STARTING THE ACCOUNT UNBLOCK OPERATIONS!");
        System.out.println("========================================");

        String responseCode = "";
        AccountUnblockResponse response = buildResponse(request);

        if (isReferenceCodeValid(request.getReferenceCode(), request.getTargetAccountNumber()) || isReferenceCodeDuplicate(response.getReferenceCode())){
            responseCode = accountUnblockOps.doAccountUnblock(request.getTargetAccountNumber());
        } else {
            log.info("Reference code of {} has been used for an acount unblock operation!", request.getReferenceCode());
            responseCode= NO_ACTION_TAKEN;
        }

        if (responseCode.equals(APPROVED_OR_COMPLETED_SUCCESSFULLY)){
            responseCode = updateAccountUnblock(request.getReferenceCode(), request.getTargetAccountNumber());
        } else {
            response.setResponseCode(UNSUCCESSFUL_ACCOUNT_OR_AMOUNT_BLOCK);
        }
        response.setResponseCode(responseCode);
        return response;
    }

    private AccountUnblockResponse buildResponse(AccountUnblockRequest request) {
        AccountUnblockResponse response = new AccountUnblockResponse();
        response.setSessionId(request.getSessionId());
        response.setDestinationInstitutionCode(request.getDestinationInstitutionCode());
        response.setChannelCode(request.getChannelCode());
        response.setReferenceCode(request.getReferenceCode());
        response.setTargetAccountNumber(request.getTargetAccountNumber());
        response.setTargetAccountName(request.getTargetAccountName());
        response.setTargetBankVerificationNumber(request.getTargetBankVerificationNumber());
        response.setReasonCode(request.getReasonCode());
        response.setNarration(request.getNarration());
        return response;
    }

    private boolean isReferenceCodeDuplicate(String referenceCode){
        return accountUnblockOps.queryUsedReferenceCode(referenceCode).size() == 0;
    }

    private boolean isReferenceCodeValid(String referenceCode, String accountNumber){
        return accountUnblockOps.referenceCodeValidation(referenceCode, accountNumber).size() == 0;
    }

    private String updateAccountUnblock(String referenceCode, String accountNumber){
            int result = accountUnblockOps.updateNIPAccountUnblock(referenceCode, accountNumber);
            if (result == 1){
                return APPROVED_OR_COMPLETED_SUCCESSFULLY;
            }
        return UNSUCCESSFUL_ACCOUNT_OR_AMOUNT_BLOCK;
    }
}
