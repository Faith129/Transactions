package com.neptunesoftware.nipintegrator.inward.repository.mappers;

import com.neptunesoftware.nipintegrator.inward.repository.database_results.MandateAdvice;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MandateAdviceMapper implements RowMapper<MandateAdvice> {
    @Override
    public MandateAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {
        return MandateAdvice.builder()
                .sessionId(rs.getString("session_id"))
                .destinationInstitutionCode(rs.getString("destination_institution_code"))
                .channelCode(rs.getInt("channel_code"))
                .mandateReferenceNumber(rs.getString("mandate_reference_number"))
                .amount(rs.getBigDecimal("amount"))
                .debitAccountName(rs.getString("deb_account_name"))
                .debitAccountNumber(rs.getString("deb_account_number"))
                .debitAccountBVN(rs.getString("deb_bvn"))
                .debKycLevel(rs.getInt("deb_bvn"))
                .beneficiaryAccountName(rs.getString("ben_account_name"))
                .beneficiaryAccountNumber(rs.getString("ben_account_number"))
                .beneficiaryAccountBVN(rs.getString("ben_bvn"))
                .benKycLevel(rs.getInt("ben_kyclevel"))
                .responseCode(rs.getString("response_code"))
                .createDate(rs.getTimestamp("create_date"))
                .build();
    }
}
