package com.neptunesoftware.nipintegrator.inward.repository.impl;

import com.neptunesoftware.nipintegrator.inward.repository.database_results.NameEnquiry;
import com.neptunesoftware.nipintegrator.inward.repository.NameEnquiryOps;
import com.neptunesoftware.nipintegrator.inward.repository.mappers.NameEnquiryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.neptunesoftware.nipintegrator.inward.repository.SqlQueries.NAME_ENQUIRY;

@Repository
@RequiredArgsConstructor
@Slf4j
public class NameEnquiryOpsImpl implements NameEnquiryOps {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public List<NameEnquiry> queryAccountName(String accountNumber) {
        log.info("Querying for account name with accountNumber of {}", accountNumber);
        System.out.println();
        return jdbcTemplate.query(NAME_ENQUIRY, new NameEnquiryMapper(), accountNumber);
    }
}
