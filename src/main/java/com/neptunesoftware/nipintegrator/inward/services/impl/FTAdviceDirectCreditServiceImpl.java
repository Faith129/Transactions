package com.neptunesoftware.nipintegrator.inward.services.impl;

import com.neptunesoftware.nipintegrator.inward.model.fundtransferadvicedirectcredit.FundTransferAdviceDirectCreditRequest;
import com.neptunesoftware.nipintegrator.inward.model.fundtransferadvicedirectcredit.FundTransferAdviceDirectCreditResponse;
import com.neptunesoftware.nipintegrator.inward.repository.FTAdviceDirectCreditOps;
import com.neptunesoftware.nipintegrator.inward.repository.database_results.NIPTransferDirectCreditAdvice;
import com.neptunesoftware.nipintegrator.inward.services.BalanceEnquiryService;
import com.neptunesoftware.nipintegrator.inward.services.FTAdviceDirectCreditService;
import com.neptunesoftware.nipintegrator.outward.api.TransactionProperties;
import com.neptunesoftware.nipintegrator.outward.service.TSQOutwardService;
import com.neptunesoftware.nipintegrator.outward.api.responsemodel.ResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.neptunesoftware.nipintegrator.NIP.constants.NIPResponseCodes.*;
import static com.neptunesoftware.nipintegrator.NIP.constants.ResponseConstants.SUCCEESS_CODE;

@Service
@RequiredArgsConstructor
public class FTAdviceDirectCreditServiceImpl implements FTAdviceDirectCreditService {

    public static final String DEBIT_CREDIT_FLAG = "CR";
    private final FTAdviceDirectCreditOps ftAdviceDirectCreditOps;
    private final TSQOutwardService tsqOutwardService;
    private final BalanceEnquiryService balanceEnquiryService;

    @Override
    public FundTransferAdviceDirectCreditResponse doFundTransferAdviceDirectCredit(FundTransferAdviceDirectCreditRequest request) {

        System.out.println("==============================================");
        System.out.println("STARTING FUND TRANSFER REVERSAL OPERATIONS");
        System.out.println("==============================================");
        System.out.println("ACCOUNT BALANCE IN ACCOUNT " + request.getOriginatorAccountNumber() + " BEFORE TRANSACTION IS " + balanceEnquiryService.getBalance(
                request.getOriginatorAccountNumber()).getAvailableBalance());



        FundTransferAdviceDirectCreditResponse response = buildResponseFrom(request);
        List<NIPTransferDirectCreditAdvice> advices = ftAdviceDirectCreditOps.performFundTransferCreditAdvice(request.getSessionId());

        if (advices.size() == 0) {
            response.setResponseCode(SYSTEM_MALFUNCTION);
            return response;
        }
        BigDecimal amount = advices.get(0).getAmount();

        System.out.println("REVERSING AN AMOUNT OF " + amount + " FROM " + request.getBeneficiaryAccountNumber() + " TO " + request.getOriginatorAccountNumber());


        ResponseModel model;
        if (ftAdviceDirectCreditOps.isSessionIDValid(request.getSessionId()) || ftAdviceDirectCreditOps.isTXNReversedAlready(request.getSessionId())) {

            TransactionProperties properties = TransactionProperties.builder()
                    .toAccountNumber(request.getOriginatorAccountNumber())
                    .fromAccountNumber(request.getBeneficiaryAccountNumber())
                    .debitCreditFlag(DEBIT_CREDIT_FLAG)
                    .transactionAmount(amount)
                    .isReversal(true)
                    .transactionDescription(request.getNarration())
                    .transactionReference(response.getSessionId())
                    .build();

            model = tsqOutwardService.postTransaction(properties);
        } else {
            response.setResponseCode(NO_ACTION_TAKEN);
            return response;
        }

        System.out.println("ACCOUNT BALANCE IN ACCOUNT " + request.getOriginatorAccountNumber() + " AFTER TRANSACTION IS " + balanceEnquiryService.getBalance(
                request.getOriginatorAccountNumber()).getAvailableBalance());

        if (model.getResponseCode().equals(SUCCEESS_CODE)){
            ftAdviceDirectCreditOps.updateFundReversalStatus(request.getSessionId());
            response.setResponseCode(APPROVED_OR_COMPLETED_SUCCESSFULLY);} else  response.setResponseCode(SYSTEM_MALFUNCTION);

        return response;
    }

    private FundTransferAdviceDirectCreditResponse buildResponseFrom(FundTransferAdviceDirectCreditRequest request) {
        FundTransferAdviceDirectCreditResponse response = new FundTransferAdviceDirectCreditResponse();
        response.setSessionId(request.getSessionId());
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
        response.setNarration(request.getNarration());
        response.setPaymentReference(request.getPaymentReference());
        response.setAmount(request.getAmount());

        return response;
    }
}
