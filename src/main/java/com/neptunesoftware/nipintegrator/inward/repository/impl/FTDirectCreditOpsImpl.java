package com.neptunesoftware.nipintegrator.inward.repository.impl;

import com.neptunesoftware.nipintegrator.NIP.constants.NIPResponseCodes;
import com.neptunesoftware.nipintegrator.inward.model.fundtransferdirectcredit.FundTransferDirectCreditRequest;
import com.neptunesoftware.nipintegrator.inward.model.fundtransferdirectcredit.FundTransferDirectCreditResponse;
import com.neptunesoftware.nipintegrator.inward.repository.FTDirectCreditOps;
import com.neptunesoftware.nipintegrator.outward.api.NIPRequestType;
import com.neptunesoftware.nipintegrator.outward.service.TSQOutwardService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.Executors;

import static com.neptunesoftware.nipintegrator.inward.repository.SqlQueries.IS_ACCOUNT_BLOCKED;

@RequiredArgsConstructor
@Repository
public class FTDirectCreditOpsImpl implements FTDirectCreditOps {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public boolean isAccountBlocked(String accountNumber) {
        String accountStatus = jdbcTemplate.queryForObject(IS_ACCOUNT_BLOCKED, String.class, accountNumber);
        assert accountStatus != null;
        return accountStatus.trim().equals("Locked");
    }




}
