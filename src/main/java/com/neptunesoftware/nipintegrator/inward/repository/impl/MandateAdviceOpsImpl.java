package com.neptunesoftware.nipintegrator.inward.repository.impl;

import com.neptunesoftware.nipintegrator.NIP.constants.NIPResponseCodes;
import com.neptunesoftware.nipintegrator.inward.model.mandateadvice.MandateAdviceResponse;
import com.neptunesoftware.nipintegrator.inward.repository.MandateAdviceOps;
import com.neptunesoftware.nipintegrator.inward.repository.database_results.MandateAdvice;
import com.neptunesoftware.nipintegrator.inward.repository.mappers.MandateAdviceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.neptunesoftware.nipintegrator.inward.repository.SqlQueries.SELECT_MANDATE_ADVICE;
import static com.neptunesoftware.nipintegrator.inward.repository.SqlQueries.UPDATE_MANDATE_ADVICE_TABLE;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MandateAdviceOpsImpl implements MandateAdviceOps {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public int saveMandateReferenceNum(MandateAdviceResponse response) {
        log.info("Updating NIP mandate advice table with mandate reference num {}", response.getMandateReferenceNumber());
        return jdbcTemplate.update(UPDATE_MANDATE_ADVICE_TABLE, response.getSessionId(),
                response.getDestinationInstitutionCode(),
                response.getChannelCode(),
                response.getMandateReferenceNumber(),
                response.getAmount(),
                response.getDebitAccountName(),
                response.getDebitAccountNumber(),
                response.getDebitBankVerificationNumber(),
                response.getDebitKYCLevel(),
                response.getBeneficiaryAccountName(),
                response.getBeneficiaryAccountNumber(),
                response.getBeneficiaryBankVerificationNumber(),
                response.getBeneficiaryKYCLevel(),
                response.getResponseCode());
    }

    @Override
    public List<MandateAdvice> queryMandateAdvice(String mandateReference, String approvedOrCompletedSuccessfully) {
        log.info("Query the database for mandate reference number of successful transactions");
        return jdbcTemplate.query(SELECT_MANDATE_ADVICE, new MandateAdviceMapper(),
                mandateReference, NIPResponseCodes.APPROVED_OR_COMPLETED_SUCCESSFULLY);
    }
}
