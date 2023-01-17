package com.neptunesoftware.nipintegrator.inward.repository.mappers;

import com.neptunesoftware.nipintegrator.inward.repository.database_results.MandateAmount;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MandateAmountMapper implements RowMapper<MandateAmount> {

    @Override
    public MandateAmount mapRow(ResultSet rs, int rowNum) throws SQLException {
        return MandateAmount.builder()
                .mandateAmount(rs.getBigDecimal("amount"))
                .build();

}}
