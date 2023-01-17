package com.neptunesoftware.nipintegrator.inward.repository.mappers;


import com.neptunesoftware.nipintegrator.inward.repository.database_results.AmountBlock;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AmountBlockMapper implements RowMapper<AmountBlock> {
    @Override
    public AmountBlock mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AmountBlock.builder()
                .sessionId(rs.getString("session_id"))
                .destinationInstitutionCode(rs.getString("destination_institution_code"))
                .channelCode(rs.getInt("channel_code"))
                .referenceCode(rs.getString("reference_code"))
                .targetAccountName(rs.getString("target_account_name"))
                .targetAccountNumber(rs.getString("target_account_number"))
                .targetBankVerificationNumber(rs.getString("target_bvn"))
                .reasonCode(rs.getString("reason_code"))
                .narration(rs.getString("narration"))
                .blockStatus(rs.getString("block_status"))
                .amount(rs.getBigDecimal("amount"))
                .balanceAmount(rs.getBigDecimal("balance_amount"))
                .responseCode(rs.getString("response_code"))
                .createDate(rs.getTimestamp("create_date"))
                .build();

    }

}
