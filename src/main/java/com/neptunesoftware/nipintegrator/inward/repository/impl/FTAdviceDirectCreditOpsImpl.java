package com.neptunesoftware.nipintegrator.inward.repository.impl;

import com.neptunesoftware.nipintegrator.inward.repository.FTAdviceDirectCreditOps;
import com.neptunesoftware.nipintegrator.inward.repository.database_results.NIPTransferDirectCreditAdvice;
import com.neptunesoftware.nipintegrator.inward.repository.mappers.NIPTransferDirectCreditMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

import static com.neptunesoftware.nipintegrator.inward.repository.SqlQueries.*;

@Repository
@RequiredArgsConstructor
public class FTAdviceDirectCreditOpsImpl implements FTAdviceDirectCreditOps {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<NIPTransferDirectCreditAdvice> performFundTransferCreditAdvice(String id) {
     return jdbcTemplate.query(PERFORM_FUND_TRANSFER_CREDIT_ADVICE, new NIPTransferDirectCreditMapper(), id);
    }

    @Override
    public boolean isSessionIDValid(String sessionID) {
        return jdbcTemplate.query(IS_SESSION_ID_VALID, new NIPTransferDirectCreditMapper(), sessionID).size() == 0;
    }

    @Override
    public boolean isTXNReversedAlready(String sessionID) {
        return jdbcTemplate.query(IS_TXN_REVERSED_ALREADY, new NIPTransferDirectCreditMapper(), sessionID) .size() == 0;
    }

    @Override
    public void updateFundReversalStatus(String sessionID) {
        long millis = new java.util.Date().getTime();
        Timestamp date = new Timestamp(millis);
        jdbcTemplate.update(UPDATE_FUND_TRANSFER_REVERSAL_STATUS, date, sessionID);
    }

}
