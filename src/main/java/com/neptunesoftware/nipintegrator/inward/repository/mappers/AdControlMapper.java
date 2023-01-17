package com.neptunesoftware.nipintegrator.inward.repository.mappers;

import com.neptunesoftware.nipintegrator.inward.repository.database_results.AdControl;
import com.neptunesoftware.nipintegrator.inward.repository.database_results.BalanceEnquiry;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdControlMapper implements RowMapper<AdControl> {
    @Override
    public AdControl mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AdControl.builder()
                .AVAIL_BAL(rs.getString("avail_bal"))
                .COLL_BAL(rs.getString("coll_bal"))
                .POST_BAL(rs.getString("post_bal"))
                .build();
    }
}
