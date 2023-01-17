package com.neptunesoftware.nipintegrator.inward.services.impl;

import com.neptunesoftware.nipintegrator.NIP.constants.ResponseConstants;
import com.neptunesoftware.nipintegrator.inward.model.fundtransferdirectdebit.FundTransferDirectDebitRequest;
import com.neptunesoftware.nipintegrator.inward.model.fundtransferdirectdebit.FundTransferDirectDebitResponse;
import com.neptunesoftware.nipintegrator.inward.repository.FundTransferDirectDebitOps;
import com.neptunesoftware.nipintegrator.inward.services.BalanceEnquiryService;
import com.neptunesoftware.nipintegrator.inward.services.FundTransferDirectDebitService;
import com.neptunesoftware.nipintegrator.outward.api.TransactionProperties;
import com.neptunesoftware.nipintegrator.outward.service.TSQOutwardService;
import com.neptunesoftware.nipintegrator.outward.api.responsemodel.ResponseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

import static com.neptunesoftware.nipintegrator.NIP.constants.NIPResponseCodes.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FundTransferDirectDebitServiceImpl implements FundTransferDirectDebitService {

    private final BalanceEnquiryService balanceEnquiryService;
    private final FundTransferDirectDebitOps directDebitOps;
    private final TSQOutwardService tsqOutwardService;

    @Override
    public FundTransferDirectDebitResponse doFundTransferDirectDebit(FundTransferDirectDebitRequest request) {
        System.out.println("==============================================");
        System.out.println("STARTING FUND TRANSFER DIRECT DEBIT OPERATIONS");
        System.out.println("==============================================");
        System.out.println("ACCOUNT BALANCE IN ACCOUNT " + request.getDebitAccountNumber() + " BEFORE TRANSACTION IS " + balanceEnquiryService.getBalance(
                request.getDebitAccountNumber()).getAvailableBalance());
        System.out.println("TRANSFERRING AN AMOUNT OF " + request.getAmount() + " FROM " + request.getDebitAccountNumber() + " TO " + request.getBeneficiaryAccountNumber());

        FundTransferDirectDebitResponse response = buildResponseFrom(request);

        if (!directDebitOps.isFundTransferDirectDebitSaved(response)) {
            response.setResponseCode(SYSTEM_MALFUNCTION);
        }

        if (response.getResponseCode().equals(APPROVED_OR_COMPLETED_SUCCESSFULLY)) {
            BigDecimal totalDebitAmount = request.getAmount().add(request.getTransactionFee());
            TransactionProperties properties = TransactionProperties.builder()
                    .isReversal(false)
                    .fromAccountNumber(request.getDebitAccountNumber())
                    .debitCreditFlag("DR")
                    .transactionAmount(totalDebitAmount)
                    .transactionDescription(request.getNarration())
                    .transactionReference(request.getSessionId())
                    .toAccountNumber(request.getBeneficiaryAccountNumber())
                    .build();
            
            ResponseModel responseModel = tsqOutwardService.postTransaction(properties);
            if (!responseModel.getResponseCode().trim().equals(ResponseConstants.SUCCEESS_CODE)){
                response.setResponseCode(SYSTEM_MALFUNCTION);
            }
            System.out.println("ACCOUNT BALANCE IN ACCOUNT " + request.getDebitAccountNumber() + " AFTER TRANSACTION IS " + balanceEnquiryService.getBalance(
                    request.getDebitAccountNumber()).getAvailableBalance());
            directDebitOps.saveIntoFundTransferDirectDebit(response);
        }
        return response;
    }

    private FundTransferDirectDebitResponse buildResponseFrom(FundTransferDirectDebitRequest request) {
        FundTransferDirectDebitResponse response = new FundTransferDirectDebitResponse();
        response.setSessionId(request.getSessionId());
        response.setNameEnquiryRef(request.getNameEnquiryRef());
        response.setDestinationInstitutionCode(request.getDestinationInstitutionCode());
        response.setChannelCode(request.getChannelCode());

        response.setBeneficiaryAccountName(request.getBeneficiaryAccountName());
        response.setBeneficiaryAccountNumber(request.getBeneficiaryAccountNumber());
        response.setBeneficiaryBankVerificationNumber(request.getBeneficiaryBankVerificationNumber());
        response.setBeneficiaryKYCLevel(request.getBeneficiaryKYCLevel());

        response.setDebitAccountName(request.getDebitAccountName());
        response.setDebitAccountNumber(request.getDebitAccountNumber());
        response.setDebitBankVerificationNumber(request.getDebitBankVerificationNumber());
        response.setDebitKYCLevel(request.getDebitKYCLevel());

        response.setTransactionLocation(request.getTransactionLocation());
        response.setTransactionFee(request.getTransactionFee());
        response.setNarration(request.getNarration());
        response.setAmount(request.getAmount());
        response.setMandateReferenceNumber(request.getMandateReferenceNumber());
        response.setPaymentReference(request.getPaymentReference());
        response.setResponseCode(validateRequest(request));
        return response;
    }

    private String validateRequest(FundTransferDirectDebitRequest request) {

        BigDecimal availableBalance = balanceEnquiryService.getBalance(request.getDebitAccountNumber())
                .getAvailableBalance();

        BigDecimal totalDebitAmount = request.getAmount().add(request.getTransactionFee());

        BigDecimal mandateAmount = directDebitOps.getMandateAmount(request.getMandateReferenceNumber());

        if (directDebitOps.isAccountBlocked(request.getDebitAccountNumber())) {
            System.out.println("ACCOUNT IS BLOCKED! TRANSACTION NOT PERMITTED");
            return TRANSACTION_NOT_PERMITTED_TO_SENDER;
        }

        if (totalDebitAmount.compareTo(availableBalance) > 0) {
            System.out.println("INSUFFICIENT FUNDS");
            return NO_SUFFICIENT_FUNDS;
        }

        if (request.getAmount().compareTo(mandateAmount) > 0) {
            System.out.println("INVALID AMOUNT");
            return INVALID_AMOUNT;
        }

        if (Objects.equals(mandateAmount, BigDecimal.ZERO)) {
            System.out.println("UNABLE TO LOCATE RECORD");
            return UNABLE_TO_LOCATE_RECORD;
        }
        return APPROVED_OR_COMPLETED_SUCCESSFULLY;
    }
}
