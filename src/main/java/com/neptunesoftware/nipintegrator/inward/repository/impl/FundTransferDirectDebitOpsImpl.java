package com.neptunesoftware.nipintegrator.inward.repository.impl;

import com.neptunesoftware.nipintegrator.NIP.constants.NIPResponseCodes;
import com.neptunesoftware.nipintegrator.inward.model.fundtransferdirectdebit.FundTransferDirectDebitResponse;
import com.neptunesoftware.nipintegrator.inward.repository.FundTransferDirectDebitOps;
import com.neptunesoftware.nipintegrator.inward.repository.mappers.AccountStatusMapper;
import com.neptunesoftware.nipintegrator.inward.repository.mappers.MandateAmountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

import static com.neptunesoftware.nipintegrator.inward.repository.SqlQueries.*;

@RequiredArgsConstructor
@Repository
@Slf4j
public class FundTransferDirectDebitOpsImpl implements FundTransferDirectDebitOps {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveIntoFundTransferDirectDebit(FundTransferDirectDebitResponse response) {
        jdbcTemplate.update(SAVE_INTO_NIP_XTER_DIRECT_CREDIT, response.getSessionId(),
                response.getNameEnquiryRef(), response.getDestinationInstitutionCode(),
                response.getChannelCode(), response.getBeneficiaryAccountName(),
                response.getBeneficiaryAccountNumber(), response.getBeneficiaryBankVerificationNumber(),
                Integer.parseInt(response.getBeneficiaryKYCLevel()), response.getDebitAccountName(),
                response.getDebitAccountNumber(), response.getDebitBankVerificationNumber(),
                Integer.parseInt(response.getDebitKYCLevel()), response.getTransactionLocation(),
                response.getNarration(), response.getPaymentReference(),
                response.getMandateReferenceNumber(), response.getTransactionFee(),
                response.getAmount(), response.getResponseCode());
    }

    @Override
    public boolean isAccountBlocked(String debitAccountNumber) {
        log.info("Querying the database to confirm if {} is blocked",  debitAccountNumber);
        String status = jdbcTemplate.query(ACCOUNT_STATUS, new AccountStatusMapper(), debitAccountNumber)
                .get(0).getStatus();
        return status.trim().equals("Locked");
    }

    @Override
    public BigDecimal getMandateAmount(String mandateReferenceNumber) {
        String  referenceCode = NIPResponseCodes.APPROVED_OR_COMPLETED_SUCCESSFULLY;
        return jdbcTemplate.query(GET_MANDATE_AMOUNT, new MandateAmountMapper(), mandateReferenceNumber, referenceCode)
                .get(0).getMandateAmount();
    }

    @Override
    public boolean isFundTransferDirectDebitSaved(FundTransferDirectDebitResponse response) {
       return jdbcTemplate.update(SAVE_NIP_TSQ, response.getSessionId(), response.getDestinationInstitutionCode(),
               response.getChannelCode(), response.getResponseCode()) == 0;
    }
}
