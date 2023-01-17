package com.neptunesoftware.nipintegrator.inward.repository.impl;

import com.neptunesoftware.nipintegrator.NIP.constants.NIPResponseCodes;
import com.neptunesoftware.nipintegrator.inward.model.amountunblock.AmountUnblockRequest;
import com.neptunesoftware.nipintegrator.inward.repository.AmountUnblockOps;
import com.neptunesoftware.nipintegrator.inward.repository.mappers.AccountTypeMapper;
import com.neptunesoftware.nipintegrator.inward.repository.mappers.AmountBlockMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

import static com.neptunesoftware.nipintegrator.inward.repository.SqlQueries.*;

@Repository
@Slf4j
@RequiredArgsConstructor
public class AmountUnblockOpsImpl implements AmountUnblockOps {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public String doAmountUnBlock(AmountUnblockRequest request) {

        log.info("Do amount unblock with accountNumber={} and amount of {}", request.getTargetAccountNumber(), request.getAmount());
        String accountType = jdbcTemplate.query(SELECT_ACCOUNT_TYPE, new AccountTypeMapper(), request.getTargetAccountNumber())
                .get(0)
                .getAccountType();

        int updateResult = jdbcTemplate.update(UPDATE_DP_DISPLAY_AMOUNT_UNBLOCK,request.getAmount(), request.getTargetAccountNumber(), accountType);

        if (updateResult == 0){
            updateResult = jdbcTemplate.update(UPDATE_GB_HOLD_STOP_AMOUNT_UNBLOCK, request.getTargetAccountNumber(), accountType, request.getReferenceCode());
        }

        if (updateResult == 0) return NIPResponseCodes.APPROVED_OR_COMPLETED_SUCCESSFULLY;

        return NIPResponseCodes.UNSUCCESSFUL_ACCOUNT_OR_AMOUNT_BLOCK;
    }

    @Override
    public boolean isReferenceCodeValid(String referenceCode) {
        log.info("Checking to see if referenceCode={} is valid.", referenceCode);
        return jdbcTemplate.query(IS_REFERENCE_CODE_VALID, new AmountBlockMapper(), referenceCode)
                .size() > 0;
    }

    @Override
    public BigDecimal getBlockedAmount(String referenceCode, String accountNumber) {
        log.info("Get the blocked amount with account number={} and referenceCode={}.", accountNumber, referenceCode);
        return jdbcTemplate.query(GET_BLOCKED_AMOUNT, new AmountBlockMapper(), referenceCode, accountNumber)
                .get(0).getAmount();
    }

    @Override
    public boolean isReferenceCodesUsed(String referenceCode) {
        log.info("checking to see if referenceCode={} is valid.", referenceCode);
        return jdbcTemplate.query(IS_REFERENCE_CODE_USED_BEFORE_FOR_UNBLOCK, new AmountBlockMapper(), referenceCode)
                .size()>0;
    }

    @Override
    public int partialAmountUnblock(AmountUnblockRequest request, BigDecimal amountLeft) {
        log.info("Updating partial block of amount={} with request={}.", amountLeft, request);
        return jdbcTemplate.update(PARTIAL_AMOUNT_UNBLOCK, request, amountLeft);
    }

    @Override
    public int fullAmountUnblock(AmountUnblockRequest request) {
        log.info("Doing full amount block with amount of {}, accountNumber of {} and referenceCode 0f {}.",
                request.getAmount(),
                request.getTargetAccountNumber(),
                request.getReferenceCode());
        return jdbcTemplate.update(FULL_AMOUNT_UNBLOCK, request.getReferenceCode(), request.getTargetAccountNumber());
    }
}
