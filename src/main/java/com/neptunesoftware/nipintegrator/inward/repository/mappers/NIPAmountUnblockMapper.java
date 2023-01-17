package com.neptunesoftware.nipintegrator.inward.repository.mappers;


import com.neptunesoftware.nipintegrator.inward.repository.database_results.NIPAmountUnblock;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class NIPAmountUnblockMapper implements RowMapper<NIPAmountUnblock> {
    @Override
    public NIPAmountUnblock mapRow(ResultSet rs, int rowNum) throws SQLException {
        return NIPAmountUnblock.builder()
                .targetAccountNumber(rs.getString("target_account_number"))
                .channelCode(rs.getInt("channel_code"))
                .destinationInstitutionCode(rs.getString("destination_institution_code"))
                .blockStatus(rs.getString("block_status"))
                .narration(rs.getString(rs.getString("narration")))
                .reasonCode(rs.getString("reason_code"))
                .referenceCode(rs.getString("reference_code"))
                .responseCode(rs.getString("reason_code"))
                .sessionId(rs.getString("session_id"))
                .targetAccountName(rs.getString("target_account_name"))
                .targetBVN(rs.getString("target_bvn"))
                .createDate(rs.getTimestamp("created_date"))
                .build();
    }
}
