package com.neptunesoftware.nipintegrator.inward.repository.mappers;

import com.neptunesoftware.nipintegrator.inward.repository.database_results.AccountType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountTypeMapper implements RowMapper<AccountType> {
    @Override
    public AccountType mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AccountType.builder()
                .accountType(rs.getString("acct_type"))
                .build();
    }
}
