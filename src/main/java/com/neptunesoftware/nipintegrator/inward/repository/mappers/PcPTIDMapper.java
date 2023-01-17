package com.neptunesoftware.nipintegrator.inward.repository.mappers;

import com.neptunesoftware.nipintegrator.inward.repository.database_results.PcPTID;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PcPTIDMapper implements RowMapper<PcPTID> {
    @Override
    public PcPTID mapRow(ResultSet rs, int rowNum) throws SQLException {
        return PcPTID.builder()
                .tableName(rs.getString("table_name"))
                .build();
    }
}
