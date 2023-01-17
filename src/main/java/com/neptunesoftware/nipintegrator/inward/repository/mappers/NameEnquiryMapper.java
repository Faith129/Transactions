package com.neptunesoftware.nipintegrator.inward.repository.mappers;

import com.neptunesoftware.nipintegrator.inward.repository.database_results.NameEnquiry;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NameEnquiryMapper implements RowMapper<NameEnquiry> {
    @Override
    public NameEnquiry mapRow(ResultSet rs, int rowNum) throws SQLException {
        return NameEnquiry
                .builder()
                .accountName(rs.getString("acct_no"))
                .bvn(rs.getString("bvn"))
                .title(rs.getString("name"))
                .build();
    }
}
