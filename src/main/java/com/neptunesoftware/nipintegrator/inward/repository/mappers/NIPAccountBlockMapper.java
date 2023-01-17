package com.neptunesoftware.nipintegrator.inward.repository.mappers;

import com.neptunesoftware.nipintegrator.inward.repository.database_results.NIPAccountBlock;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class NIPAccountBlockMapper implements RowMapper<NIPAccountBlock> {
    @Override
    public NIPAccountBlock mapRow(ResultSet rs, int rowNum) throws SQLException {
        return NIPAccountBlock.builder()
                .sessionId(rs.getString("session_id"))
                .destinationInstitutionCode(rs.getString("destination_institution_code"))
                .channelCode(rs.getInt("channel_code"))
                .referenceCode(rs.getString("reference_code"))
                .targetAccountName(rs.getString("target_account_name"))
                .targetAccountNumber(rs.getString("target_account_number"))
                .targetBVN(rs.getString("target_bvn"))
                .responseCode(rs.getString("response_code"))
                .reasonCode(rs.getString("reason_code"))
                .narration(rs.getString("narration"))
                .lockStatus(rs.getString("lock_status"))
                .build();
    }
}
