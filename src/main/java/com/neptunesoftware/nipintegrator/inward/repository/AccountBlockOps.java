package com.neptunesoftware.nipintegrator.inward.repository;

import com.neptunesoftware.nipintegrator.NIP.constants.NIPResponseCodes;
import com.neptunesoftware.nipintegrator.inward.model.accountblock.AccountBlockRequest;
import com.neptunesoftware.nipintegrator.inward.repository.database_results.AccountType;
import com.neptunesoftware.nipintegrator.inward.repository.database_results.NIPAccountBlock;
import com.neptunesoftware.nipintegrator.inward.repository.mappers.AccountTypeMapper;
import com.neptunesoftware.nipintegrator.inward.repository.mappers.NIPAccountBlockMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.neptunesoftware.nipintegrator.inward.repository.SqlQueries.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AccountBlockOps {
    private final JdbcTemplate jdbcTemplate;

    public boolean saveAccountBlock(AccountBlockRequest ar, String responseCode) {
        int result = jdbcTemplate.update(INSERT_ACCT_BLOCK,
                                ar.getSessionId(),
                                        ar.getDestinationInstitutionCode(),
                                        ar.getChannelCode(),
                                        ar.getReferenceCode(),
                                        ar.getTargetAccountName(),
                                        ar.getTargetAccountNumber(),
                                        ar.getTargetBankVerificationNumber(),
                                        ar.getReasonCode(),
                                        ar.getNarration(),
                                        "Y",
                                        responseCode);

        return result >= 0;
    }

    public  boolean isReferenceCodeUnique(String referenceCode) {
      int result =jdbcTemplate.query(ACCOUNT_BLOCK_REFERENCE_CODE, new NIPAccountBlockMapper(), referenceCode).size();
      return result > 0;
    }

    public String performAccountBlock(AccountBlockRequest accountBlockRequest) {
        int result = executeAccountBlock(accountBlockRequest);
        return result >= 0 ? NIPResponseCodes.APPROVED_OR_COMPLETED_SUCCESSFULLY
                : NIPResponseCodes.UNSUCCESSFUL_ACCOUNT_OR_AMOUNT_BLOCK;
    }

    public int executeAccountBlock(AccountBlockRequest accountBlockRequest) {
        // ** First select account type
        String accountType = selectAccountType(accountBlockRequest.getTargetAccountNumber());
        int result = jdbcTemplate.update(DP_ACCT, accountBlockRequest.getTargetAccountNumber(),
                accountType.trim().toUpperCase());
        if(result >= 0) {
            result = jdbcTemplate.update(DP_DISPLAY, accountBlockRequest.getTargetAccountNumber(),
                    accountType.trim().toUpperCase());
        }
        return result;
    }

    private String selectAccountType(String accountNumber) {
        String resultAccType = jdbcTemplate.queryForObject(ACCT_TYPE, String.class, accountNumber);
        String accountType =    queryAccountType(accountNumber).get(0).getAccountType();
        if (resultAccType == null) {
            resultAccType = accountType;
        }
        return resultAccType;
    }

    private List<AccountType> queryAccountType(final String accountNumber){
        log.info("Querying for account type with accountNumber of {}", accountNumber);
        return jdbcTemplate.query(SELECT_ACCOUNT_TYPE, new AccountTypeMapper(), accountNumber);
    }

    private List<NIPAccountBlock> queryUsedReferenceCode(String referenceCode) {
        log.info("Selecting all used referenceCode of code={}", referenceCode);
        return jdbcTemplate.query(ACCOUNT_BLOCK_REFERENCE_CODE, new NIPAccountBlockMapper(), referenceCode);
    }

}
