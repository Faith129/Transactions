package com.neptunesoftware.nipintegrator.inward.repository.impl;

import com.neptunesoftware.nipintegrator.NIP.constants.NIPResponseCodes;
import com.neptunesoftware.nipintegrator.NIP.constants.ResponseConstants;
import com.neptunesoftware.nipintegrator.inward.model.fundtransferadvicedirectdebit.FundTransferAdviceDirectDebitRequest;
import com.neptunesoftware.nipintegrator.inward.repository.FundTransferAdviceDirectDebitOps;
import com.neptunesoftware.nipintegrator.inward.repository.mappers.DirectDebitAdviceMapper;
import com.neptunesoftware.nipintegrator.inward.repository.mappers.MandateAmountMapper;
import com.neptunesoftware.nipintegrator.outward.api.TransactionProperties;
import com.neptunesoftware.nipintegrator.outward.service.TSQOutwardService;
import com.neptunesoftware.nipintegrator.outward.api.responsemodel.ResponseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static com.neptunesoftware.nipintegrator.inward.repository.SqlQueries.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class FundTransferAdviceDirectDebitOpsImpl implements FundTransferAdviceDirectDebitOps {
    private final JdbcTemplate jdbcTemplate;
    private final TSQOutwardService tsqOutwardService;

    @Override
    public String performFundTransferDebitAdvice(FundTransferAdviceDirectDebitRequest fundTransferAdviceDirectDebitRequest) {

        BigDecimal transactionAmount = fundTransferAdviceDirectDebitRequest.getAmount().add(fundTransferAdviceDirectDebitRequest.getTransactionFee());

        // call procedure to debit account
        System.out.println(transactionAmount);
        TransactionProperties transaction = TransactionProperties.builder().build();
        transaction.setFromAccountNumber(fundTransferAdviceDirectDebitRequest.getDebitAccountNumber());
        transaction.setToAccountNumber(fundTransferAdviceDirectDebitRequest.getBeneficiaryAccountNumber());
        transaction.setDebitCreditFlag("DR");
        transaction.setTransactionAmount(transactionAmount);
        transaction.setTransactionDescription(fundTransferAdviceDirectDebitRequest.getNarration());
        transaction.setTransactionReference(fundTransferAdviceDirectDebitRequest.getSessionId());
        transaction.setReversal(true);

         ResponseModel responseModel = tsqOutwardService.postTransaction(transaction);

        return responseModel.getResponseCode().equals(ResponseConstants.SUCCEESS_CODE)
                ? NIPResponseCodes.APPROVED_OR_COMPLETED_SUCCESSFULLY
                : NIPResponseCodes.SYSTEM_MALFUNCTION;

    }

    @Override
    public boolean isSessionIDValid(String sessionID) {
      return jdbcTemplate.query(SELECT_NIP_XFER_DIRECT_DEBIT, new DirectDebitAdviceMapper(), sessionID).size() == 0;
    }

    @Override
    public boolean isTxnReversedAlready(String sessionID) {
       return jdbcTemplate.query(SELECT_NIP_XTER_DIRECT_DEBIT_REVERSED, new DirectDebitAdviceMapper(), sessionID).size() == 0;
    }


    @Override
    public boolean updateFundTansferReversalStatus(String sessionID) {
        long presentTimeInLong = new java.util.Date().getTime();
        Timestamp presentTime = new Timestamp(presentTimeInLong);
        int result = jdbcTemplate.update(UPDATE_FUND_XTER_REVERSAL_STATUS, presentTime, sessionID);
        return result > 0;
    }

    @Override
    public BigDecimal getMandateAmount(String mandateReference) {
        String referenceCode = NIPResponseCodes.APPROVED_OR_COMPLETED_SUCCESSFULLY;
        return jdbcTemplate.query(SELECT_MANDATE_AMOUNT, new MandateAmountMapper(), mandateReference, referenceCode).get(0).getMandateAmount();
    }
}