package com.neptunesoftware.nipintegrator.inward.repository.mappers;

import com.neptunesoftware.nipintegrator.inward.repository.database_results.NIPAccount;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class NIPAccountMapper implements RowMapper<NIPAccount> {
    @Override
    public NIPAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
        return NIPAccount.builder()
                .targetAccountNumber(rs.getString("target_account_number"))
                .channelCode(rs.getInt(""))
                .destinationInstitutionCode(rs.getString("destination_institution_code"))
                .lockStatus(rs.getString("lock_status"))
                .narration(rs.getString(rs.getString("narration")))
                .reasonCode(rs.getString("reason_code"))
                .referenceCode(rs.getString("reference_code"))
                .responseCode(rs.getString("reason_code"))
                .sessionId(rs.getString("session_id"))
                .targetAccountName(rs.getString("target_account_name"))
                .targetBVN(rs.getString("target_bvn"))
                .build();
    }
}
