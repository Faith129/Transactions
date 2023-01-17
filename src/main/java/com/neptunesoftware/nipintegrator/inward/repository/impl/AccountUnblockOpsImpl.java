package com.neptunesoftware.nipintegrator.inward.repository.impl;

import com.neptunesoftware.nipintegrator.NIP.constants.NIPResponseCodes;
import com.neptunesoftware.nipintegrator.inward.repository.AccountUnblockOps;
import com.neptunesoftware.nipintegrator.inward.repository.database_results.NIPAccountBlock;
import com.neptunesoftware.nipintegrator.inward.repository.mappers.AccountStatusMapper;
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
public class AccountUnblockOpsImpl implements AccountUnblockOps {

    private final JdbcTemplate jdbcTemplate;

    //TODO Refine logic to query for active accounts

    @Override
    public String doAccountUnblock(String accountNumber) {

        String accountType = jdbcTemplate.query(SELECT_ACCOUNT_TYPE, new AccountTypeMapper(), accountNumber).get(0).getAccountType();

        log.info("UPDATING DEPOSIT ACCOUNT TO ACTIVE PROCESS");
        jdbcTemplate.update(UPDATE_DP_ACCOUNT_UNBLOCK_ACCOUNT, accountNumber, accountType);

        String depositAccountStatus = getAccountStatus(accountNumber);

        if (depositAccountStatus.trim().equals("Active")){
            log.info("UPDATING DEPOSIT ACCOUNT TO ACTIVE  was successful");
            log.info("UPDATING DEPOSIT DISPLAY");
            jdbcTemplate.update(UPDATE_DP_DISPLAY_UNBLOCK_ACCOUNT, accountNumber,
                    accountType);

            String depositDisplayAccountStatus = getAccountStatus(accountNumber);
            if (depositDisplayAccountStatus.trim().equals("Active")){
                log.info("UPDATE_DP_DISPLAY_UNBLOCK_ACCOUNT was successful");
                return NIPResponseCodes.APPROVED_OR_COMPLETED_SUCCESSFULLY;
            }
        }
        return NIPResponseCodes.UNSUCCESSFUL_ACCOUNT_OR_AMOUNT_UNBLOCK;
    }

    @Override
    public List<NIPAccountBlock> queryUsedReferenceCode(String referenceCode) {
        log.info("Selecting all used referenceCode of code={}", referenceCode);
        return jdbcTemplate.query(SELECT_ALL_USED_REFERENCE_CODE, new NIPAccountBlockMapper(), referenceCode);
    }

    @Override
    public List<NIPAccountBlock> referenceCodeValidation(String referenceCode, String accountNumber) {
        log.info("Querying for code validation with code={} and accountNumber={}", referenceCode, accountNumber);
        return jdbcTemplate.query(SELECT_ALL_WITH_REFERENCE_CODE_AND_ACCT_NUM,
                new NIPAccountBlockMapper(),
                referenceCode, accountNumber);
    }

    @Override
    public int updateNIPAccountUnblock(String referenceCode, String accountNumber) {
        log.info("Updating NIP ACCOUNT TABLE with referenceCode={} and accountNumber={}", referenceCode, accountNumber);
        jdbcTemplate.update(UPDATE_NIP_ACCOUNT_BLOCK_TABLE, referenceCode, accountNumber);
        String lockStatus = jdbcTemplate.query(SELECT_ALL_WITH_REFERENCE_CODE_AND_ACCT_NUM, new NIPAccountBlockMapper(), referenceCode, accountNumber)
                .get(0).getLockStatus();

        if (lockStatus.trim().equals("N".trim())){
            log.info("ACCOUNT UNBLOCKED SUCCESSFULLY");
            return 1;
        } else return 0;
    }

    private String getAccountStatus(String accountNumber) {
        return jdbcTemplate.query(ACCOUNT_STATUS, new AccountStatusMapper(), accountNumber)
                .get(0).getStatus();
    }
}
