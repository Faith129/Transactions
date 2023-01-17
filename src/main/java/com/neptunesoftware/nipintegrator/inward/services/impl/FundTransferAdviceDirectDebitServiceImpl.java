package com.neptunesoftware.nipintegrator.inward.services.impl;

import com.neptunesoftware.nipintegrator.NIP.constants.NIPResponseCodes;
import com.neptunesoftware.nipintegrator.inward.model.fundtransferadvicedirectdebit.FundTransferAdviceDirectDebitRequest;
import com.neptunesoftware.nipintegrator.inward.model.fundtransferadvicedirectdebit.FundTransferAdviceDirectDebitResponse;
import com.neptunesoftware.nipintegrator.inward.repository.FundTransferAdviceDirectDebitOps;
import com.neptunesoftware.nipintegrator.inward.services.BalanceEnquiryService;
import com.neptunesoftware.nipintegrator.inward.services.FundTransferAdviceDirectDebitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class FundTransferAdviceDirectDebitServiceImpl implements FundTransferAdviceDirectDebitService {
    private final FundTransferAdviceDirectDebitOps fundTransferAdviceDirectDebitOps;
    private final BalanceEnquiryService balanceEnquiryService;


    public FundTransferAdviceDirectDebitResponse getFundTransferAdviceDircetDebit (FundTransferAdviceDirectDebitRequest request) {
        FundTransferAdviceDirectDebitResponse response = buildResponse(request);
        String resp = validateRequest(request);
        if (!resp.equals(NIPResponseCodes.APPROVED_OR_COMPLETED_SUCCESSFULLY)){
            response.setResponseCode(resp);
        }

        System.out.println("ACCOUNT BALANCE OF " + request.getDebitAccountNumber() + " is " +
                balanceEnquiryService.getBalance(request.getDebitAccountNumber()).getAvailableBalance() + " BEFORE TRANSACTION");

        if (fundTransferAdviceDirectDebitOps.performFundTransferDebitAdvice
                (request).equals(NIPResponseCodes.APPROVED_OR_COMPLETED_SUCCESSFULLY)) {
            //perform fund transfer debit advice
            //update details
            fundTransferAdviceDirectDebitOps.updateFundTansferReversalStatus(request.getSessionId());

            System.out.println("ACCOUNT BALANCE OF " + request.getDebitAccountNumber() + " is " +
                    balanceEnquiryService.getBalance(request.getDebitAccountNumber()).getAvailableBalance() + " AFTER TRANSACTION");
            }
        return response;
    }


    private FundTransferAdviceDirectDebitResponse buildResponse (FundTransferAdviceDirectDebitRequest request){
        FundTransferAdviceDirectDebitResponse response = new FundTransferAdviceDirectDebitResponse();
        response.setSessionId(request.getSessionId());
        response.setNameEnquiryRef(request.getNameEnquiryRef());
        response.setDestinationInstitutionCode(request.getDestinationInstitutionCode());
        response.setChannelCode(request.getChannelCode());

        response.setDebitAccountName(request.getDebitAccountName());
        response.setDebitAccountNumber(request.getDebitAccountNumber());
        response.setDebitKYCLevel(request.getDebitKYCLevel());
        response.setBeneficiaryAccountName(request.getBeneficiaryAccountName());
        response.setBeneficiaryAccountNumber(request.getBeneficiaryAccountNumber());

        response.setBeneficiaryBankVerificationNumber(request.getBeneficiaryBankVerificationNumber());
        response.setDebitBankVerificationNumber(request.getDebitBankVerificationNumber());
        response.setBeneficiaryKYCLevel(request.getBeneficiaryKYCLevel());
        response.setTransactionLocation(request.getTransactionLocation());
        response.setNarration(request.getNarration());
        response.setPaymentReference(request.getPaymentReference());
        response.setMandateReferenceNumber(request.getMandateReferenceNumber());
        response.setTransactionFee(request.getTransactionFee());
        response.setAmount(request.getAmount());
        System.out.println(response.getResponseCode());
        response.setResponseCode(validateRequest(request));
        System.out.println(response.getResponseCode());
        return response;
    }

    private String validateRequest(FundTransferAdviceDirectDebitRequest fundXferAdviceDDRequest) {

        String resp = NIPResponseCodes.APPROVED_OR_COMPLETED_SUCCESSFULLY;

       // FundTransferAdviceDirectDebitOpsImpl database = new FundTransferAdviceDirectDebitOpsImpl(new JdbcTemplate());

        BigDecimal mandateAmount = fundTransferAdviceDirectDebitOps.getMandateAmount(fundXferAdviceDDRequest.getMandateReferenceNumber());
        if(fundXferAdviceDDRequest.getAmount().compareTo(mandateAmount) > 0) {
            resp = NIPResponseCodes.INVALID_AMOUNT;
            System.out.println(resp);
        }

        // returns zero for mandateAmount when mandate record is not found
        if(Objects.equals(mandateAmount, BigDecimal.ZERO)) {
            resp = NIPResponseCodes.UNABLE_TO_LOCATE_RECORD;
        }

        if(!fundTransferAdviceDirectDebitOps.isTxnReversedAlready(fundXferAdviceDDRequest.getSessionId())) {
            resp = NIPResponseCodes.NO_ACTION_TAKEN;
        }

        //check if session id is valid
        if(fundTransferAdviceDirectDebitOps.isSessionIDValid(fundXferAdviceDDRequest.getSessionId())) {
            resp = NIPResponseCodes.INVALID_SESSION_OR_RECORD_ID;
        }

        return resp;
    }


}
