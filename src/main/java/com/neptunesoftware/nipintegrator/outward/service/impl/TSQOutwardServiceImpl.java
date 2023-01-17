package com.neptunesoftware.nipintegrator.outward.service.impl;

import com.neptunesoftware.nipintegrator.NIP.NIBSS;
import com.neptunesoftware.nipintegrator.outward.api.NIPRequestType;
import com.neptunesoftware.nipintegrator.outward.api.TransactionProperties;
import com.neptunesoftware.nipintegrator.outward.api.fundtransferdirectcreditoutward.FundTransferDirectCreditResponse;
import com.neptunesoftware.nipintegrator.outward.api.responsemodel.ResponseModel;
import com.neptunesoftware.nipintegrator.outward.api.tsq.TransactionStatusQueryResponse;
import com.neptunesoftware.nipintegrator.outward.repository.TSQOutwardOps;
import com.neptunesoftware.nipintegrator.outward.service.TSQOutwardService;
import com.neptunesoftware.nipintegrator.utilities.Marshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TSQOutwardServiceImpl implements TSQOutwardService {

    private final TSQOutwardOps tsqOutwardOps;
    private final NIBSS nibss;

    @Override
    public String transactionStatusQuery(FundTransferDirectCreditResponse response, NIPRequestType requestType) {

        String senderAccountNumber = "", beneficiaryAccountNumber = "", debitCreditFlag = "";
        TransactionStatusQueryResponse transactionStatusQueryResponse = nibss.transactionStatusQuerySingleItem(
                response.getSessionId(), response.getChannelCode());

        if (requestType.equals(NIPRequestType.INWARD)) {
            senderAccountNumber = response.getBeneficiaryAccountNumber();
            beneficiaryAccountNumber = response.getOriginatorAccountNumber();
            debitCreditFlag = "CR";
        }
        if (requestType.equals(NIPRequestType.OUTWARD)) {
            beneficiaryAccountNumber = response.getBeneficiaryAccountNumber();
            senderAccountNumber = response.getOriginatorAccountNumber();
            debitCreditFlag = "DR";
        }
        TransactionProperties properties = TransactionProperties.builder()
                .debitCreditFlag(debitCreditFlag)
                .fromAccountNumber(senderAccountNumber)
                .toAccountNumber(beneficiaryAccountNumber)
                .isReversal(false)
                .transactionAmount(response.getAmount())
                .transactionReference(response.getSessionId())
                .transactionDescription(response.getNarration())
                .build();

        postTransaction(properties);
        tsqOutwardOps.saveTSQ(transactionStatusQueryResponse);

        return Marshaller.convertObjectToXmlString(transactionStatusQueryResponse);
    }

    @Override
    public ResponseModel postTransaction(TransactionProperties properties) {
        return tsqOutwardOps.postTransactions(properties);
    }
}
