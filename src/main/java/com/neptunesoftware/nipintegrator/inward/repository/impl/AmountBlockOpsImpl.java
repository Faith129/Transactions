package com.neptunesoftware.nipintegrator.inward.repository.impl;

import com.neptunesoftware.nipintegrator.NIP.constants.NIPResponseCodes;
import com.neptunesoftware.nipintegrator.inward.model.amountblock.AmountBlockRequest;
import com.neptunesoftware.nipintegrator.inward.repository.AmountBlockOps;
import com.neptunesoftware.nipintegrator.inward.repository.database_results.AmountBlock;
import com.neptunesoftware.nipintegrator.inward.repository.mappers.AccountTypeMapper;
import com.neptunesoftware.nipintegrator.inward.repository.mappers.AdControlMapper;
import com.neptunesoftware.nipintegrator.inward.repository.mappers.AmountBlockMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.neptunesoftware.nipintegrator.inward.repository.SqlQueries.*;


@Repository
@RequiredArgsConstructor
@Slf4j
public class AmountBlockOpsImpl implements AmountBlockOps {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public boolean isAuthorizationValid(final String authCode) {
        return false;
    }


    @Override
    public boolean saveAmountBlock (AmountBlockRequest amountBlockRequest, String responseCode){
        String sql = "insert into nip_amount_block (session_id, destination_institution_code, channel_code, "
                + "reference_code, target_account_name, target_account_number, target_bvn, reason_code, narration," +
                " block_status, amount, balance_amount, response_code) \r\n"
                + " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int result = jdbcTemplate.update(sql,
                amountBlockRequest.getSessionId(),
                amountBlockRequest.getDestinationInstitutionCode(),
                amountBlockRequest.getChannelCode(),
                amountBlockRequest.getReferenceCode(),
                amountBlockRequest.getTargetAccountName(),
                amountBlockRequest.getTargetAccountNumber(),
                amountBlockRequest.getTargetBankVerificationNumber(),
                amountBlockRequest.getReasonCode(),
                amountBlockRequest.getNarration(),
                "Y",
                amountBlockRequest.getAmount(),
                amountBlockRequest.getAmount(),
                responseCode);
        return result >= 0;
    }

    @Override
    public  boolean isReferenceCodeUnique(String referenceCode) {
        return queryUsedReferenceCode(referenceCode).size() == 0;

    }


    private String selectAccountType(String accountNumber) {
      return jdbcTemplate.query(SELECT_ACCOUNT_TYPE, new AccountTypeMapper(), accountNumber).get(0).getAccountType();
    }

    private String selectEffectiveDate() {
        return jdbcTemplate.queryForObject(SELECT_EFFECTIVE_DATE, String.class);
    }

    private String selectExpiryDate(){
        return jdbcTemplate.queryForObject(SELECT_EXPIRY_DATE, String.class);
    }

    private int executePsp_get_ptid(String tableName) {
        int pTID = -1;
        int result = jdbcTemplate.update(UPDATE_PC_PTID, tableName);
        if (result == 0){
            System.out.println(tableName);
            pTID = Integer.parseInt(jdbcTemplate.queryForObject(SELECT_PTID, String.class, tableName));
            System.out.println(pTID);
        }
        return pTID;
    }


    private int executeCsp_get_dp_posting_def() {
        jdbcTemplate.queryForObject(executeCsp_get_dp_posting_def, new AdControlMapper());

        AdControlMapper records = new AdControlMapper();
        int result = -2;
        if (records == null) {
            // Exception was thrown
            result = -1;
            return result;
        }
        String availBal = "";
        String collBal = "";
        String postBal = "";

        int availBalDef = -1;
        switch (availBal.trim()) {
            case "Current - Float 1":
                availBalDef = 2;
                break;
            case "Current - Float 2":
                availBalDef = 3;
                break;
            case "Current + Memo Posted":
                availBalDef = 6;
                break;
            case "Current - Float 1 + Memo Posted":
                availBalDef = 8;
                break;
            case "Current - Float 2 + Memo Posted":
                availBalDef = 9;
                break;
            case "Current - Holds":
            case "Current - Holds - Float 1":
            case "Current - Holds - Float 2":
            case "Current - Holds + Memo Posted":
            case "Current - Holds - Float 1 + Memo Posted":
            case "Current - Holds - Float 2 + Memo Posted":
                result = -3;
                break;
            default : availBalDef = 1;
        }
        if(result == -3) return result;


        // collected balance definition
        int collBalDef = -1;
        switch (collBal.trim()) {
            case "Current - Float 1":
                collBalDef = 2;
                break;
            case "Current - Float 2":
                collBalDef = 3;
                break;
            case "Current - Holds":
            case "Current - Holds - Float 1":
            case "Current - Holds - Float 2":
            case "Current + Memo Posted":
            case "Current - Holds + Memo Posted":
            case "Current - Float 1 + Memo Posted":
            case "Current - Float 2 + Memo Posted":
            case "Current - Holds - Float 1 + Memo Posted":
            case "Current - Holds - Float 2 + Memo Posted":
                result = -4;
                break;
            default : collBalDef = 1;
        }
        if(result == -3) return result;


        // posting balance definition
        int postingBalDef = -20;
        switch (postBal.trim()) {
            case "Available":
                postingBalDef = availBalDef;
                break;
            case "Collected":
                postingBalDef = collBalDef;
                break;
            case "Current":
            default : postingBalDef = 0;
        }

        return postingBalDef;
    }

    @Override
    public int executeAmountBlock(AmountBlockRequest request) {

        String accountType = selectAccountType(request.getTargetAccountNumber());
        String effectiveDate = selectEffectiveDate();
        String expiryDate = selectExpiryDate();
        int holdId = executePsp_get_ptid("gb_hold_stop");

        String description = "Amount block on account with reference - " +
                request.getReferenceCode();

        log.info("Do amount block with accountNumber={} and amount of {}", request.getTargetAccountNumber(), request.getAmount());

        int result = jdbcTemplate.update(UPDATE_DP_DISPLAY_SET_HOLD_BAL,
                request.getAmount(),  request.getTargetAccountNumber(), accountType);

        if(result == 0) {
            jdbcTemplate.update(INSERT_INTO_GB_HOLD_STOP,
                    request.getTargetAccountNumber(),
                    accountType,
                    holdId,
                    effectiveDate,
                    holdId,
                    request.getAmount(),
                    expiryDate,
                    description,
                    request.getReferenceCode());
        }
        return result;
    }


    @Override
    public String performAmountBlock(AmountBlockRequest amountBlockRequest) {
        int result = executeAmountBlock(amountBlockRequest);
        String responseCode = result == 0 ? NIPResponseCodes.APPROVED_OR_COMPLETED_SUCCESSFULLY
                : NIPResponseCodes.UNSUCCESSFUL_ACCOUNT_OR_AMOUNT_BLOCK;
        return responseCode;
    }

    private List<AmountBlock> queryUsedReferenceCode(String referenceCode) {
        log.info("Selecting all used referenceCode of code={}", referenceCode);
        return jdbcTemplate.query(REFERENCE_CODE, new AmountBlockMapper(), referenceCode);
    }

}


