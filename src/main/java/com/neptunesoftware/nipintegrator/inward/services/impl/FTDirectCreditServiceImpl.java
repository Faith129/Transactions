package com.neptunesoftware.nipintegrator.inward.services.impl;

import com.neptunesoftware.nipintegrator.NIP.constants.NIPResponseCodes;
import com.neptunesoftware.nipintegrator.NIP.security.NIPCredential;
import com.neptunesoftware.nipintegrator.utilities.CommonMethods;
import com.neptunesoftware.nipintegrator.inward.model.fundtransferdirectcredit.FundTransferDirectCreditRequest;
import com.neptunesoftware.nipintegrator.inward.repository.FTDirectCreditOps;
import com.neptunesoftware.nipintegrator.inward.services.FTDirectCreditService;
import com.neptunesoftware.nipintegrator.outward.api.NIPRequestType;
import com.neptunesoftware.nipintegrator.outward.service.FTDirectCreditOutwardService;
import com.neptunesoftware.nipintegrator.outward.service.TSQOutwardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class FTDirectCreditServiceImpl implements FTDirectCreditService {

    private static final int TIME_DELAY_IN_MILLIS = 60_000;
    private final FTDirectCreditOps ftDirectCreditOps;
    private final TSQOutwardService tsqOutwardService;

    private final FTDirectCreditOutwardService fundTransferDirectCreditOutwardService;

    @Override
    public com.neptunesoftware.nipintegrator.outward.api.fundtransferdirectcreditoutward.FundTransferDirectCreditResponse executeFundTransfer(FundTransferDirectCreditRequest request) {

        com.neptunesoftware.nipintegrator.outward.api.fundtransferdirectcreditoutward.FundTransferDirectCreditResponse response = buildResponse(request);
        // Save transaction into database

        boolean isFundTransferSaved = fundTransferDirectCreditOutwardService.savedFundTransfer(response, NIPRequestType.INWARD);

        if (!isFundTransferSaved) {response.setResponseCode(NIPResponseCodes.SYSTEM_MALFUNCTION);}

        if (response.getResponseCode().equals(NIPResponseCodes.APPROVED_OR_COMPLETED_SUCCESSFULLY)) {

            Executors.newSingleThreadExecutor()
                    .submit( () -> {
                        try {
                            Thread.sleep(TIME_DELAY_IN_MILLIS);
                            tsqOutwardService.transactionStatusQuery(response, NIPRequestType.INWARD);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }

        return response;
    }

    private com.neptunesoftware.nipintegrator.outward.api.fundtransferdirectcreditoutward.FundTransferDirectCreditResponse buildResponse(FundTransferDirectCreditRequest request) {

        com.neptunesoftware.nipintegrator.outward.api.fundtransferdirectcreditoutward.FundTransferDirectCreditResponse response = validateRequest(request);
        response.setSessionId(response.getSessionId());
        response.setNameEnquiryRef(request.getNameEnquiryRef());
        response.setDestinationInstitutionCode(request.getDestinationInstitutionCode());
        response.setChannelCode(request.getChannelCode());
        response.setBeneficiaryAccountName(request.getBeneficiaryAccountName());
        response.setBeneficiaryAccountNumber(request.getBeneficiaryAccountNumber());
        response.setBeneficiaryBankVerificationNumber(request.getBeneficiaryBankVerificationNumber());
        response.setBeneficiaryKYCLevel(request.getBeneficiaryKYCLevel());
        response.setOriginatorAccountName(request.getOriginatorAccountName());
        response.setOriginatorAccountNumber(request.getOriginatorAccountNumber());
        response.setOriginatorBankVerificationNumber(request.getOriginatorBankVerificationNumber());
        response.setOriginatorKYCLevel(request.getOriginatorKYCLevel());
        response.setTransactionLocation(request.getTransactionLocation());
        response.setPaymentReference(request.getPaymentReference());
        response.setAmount(request.getAmount());

        return response;
    }

    public com.neptunesoftware.nipintegrator.outward.api.fundtransferdirectcreditoutward.FundTransferDirectCreditResponse validateRequest(FundTransferDirectCreditRequest request) {

        com.neptunesoftware.nipintegrator.outward.api.fundtransferdirectcreditoutward.FundTransferDirectCreditResponse response = new com.neptunesoftware.nipintegrator.outward.api.fundtransferdirectcreditoutward.FundTransferDirectCreditResponse();

        if (request.getNarration().length() > 100) {
            String narration = request.getNarration().substring(0, 100);
            response.setNarration(
                    CommonMethods.escapeSpecialCharacter(narration)
            ); //remove special characters from narration
        }
        response.setResponseCode(NIPResponseCodes.APPROVED_OR_COMPLETED_SUCCESSFULLY);

        if (request.getAmount().compareTo(new BigDecimal(
                new NIPCredential().getTransferLimit())) > 0) {
            response.setResponseCode(NIPResponseCodes.TRANSFER_LIMIT_EXCEEDED);
        }

        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            response.setResponseCode(NIPResponseCodes.INVALID_AMOUNT);
        }

        if (ftDirectCreditOps.isAccountBlocked(request.getBeneficiaryAccountNumber())) {
            response.setResponseCode(NIPResponseCodes.TRANSACTION_NOT_PERMITTED_TO_SENDER);
        }

        return response;
    }



}
