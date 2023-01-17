package com.neptunesoftware.nipintegrator.inward.repository.impl;

import com.neptunesoftware.nipintegrator.inward.repository.database_results.AccountType;
import com.neptunesoftware.nipintegrator.inward.repository.database_results.BalanceEnquiry;
import com.neptunesoftware.nipintegrator.inward.repository.BalanceEnquiryOps;
import com.neptunesoftware.nipintegrator.inward.repository.mappers.AccountTypeMapper;
import com.neptunesoftware.nipintegrator.inward.repository.mappers.BalanceEnquiryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.neptunesoftware.nipintegrator.inward.repository.SqlQueries.BALANCE_ENQUIRY;
import static com.neptunesoftware.nipintegrator.inward.repository.SqlQueries.SELECT_ACCOUNT_TYPE;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BalanceEnquiryOpsImpl implements BalanceEnquiryOps {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public boolean isAuthorizationValid(String authCode) {
        return false;
    }

    @Override
    public List<BalanceEnquiry> queryAccountBalance(String accountNumber) {
        String accountType = queryAccountType(accountNumber).get(0).getAccountType();
        log.info("Querying for account balance with accountNumber of {} and accountType {}", accountNumber, accountType);
        return jdbcTemplate.query(BALANCE_ENQUIRY, new BalanceEnquiryMapper(), accountNumber, accountType);
    }

    private List<AccountType> queryAccountType(final String accountNumber){
        log.info("Querying for account type with accountNumber of {}", accountNumber);
        return jdbcTemplate.query(SELECT_ACCOUNT_TYPE, new AccountTypeMapper(), accountNumber);
    }
}
