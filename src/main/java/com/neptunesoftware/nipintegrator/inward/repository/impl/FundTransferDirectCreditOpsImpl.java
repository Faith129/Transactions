package com.neptunesoftware.nipintegrator.inward.repository.impl;

import com.neptunesoftware.nipintegrator.inward.repository.FundTransferDirectCreditOps;
import com.neptunesoftware.nipintegrator.inward.repository.mappers.AccountStatusMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.neptunesoftware.nipintegrator.inward.repository.SqlQueries.ACCOUNT_STATUS_LOCKED;

@RequiredArgsConstructor
@Repository
@Slf4j
public class FundTransferDirectCreditOpsImpl implements FundTransferDirectCreditOps {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public boolean isAccountBlocked(String debitAccountNumber) {
        log.info("Querying the database to confirm if {} is blocked",  debitAccountNumber);
        String status = jdbcTemplate.query(ACCOUNT_STATUS_LOCKED, new AccountStatusMapper(), debitAccountNumber).get(0).getStatus();
        return status.trim().equals("Locked");
    }


}
