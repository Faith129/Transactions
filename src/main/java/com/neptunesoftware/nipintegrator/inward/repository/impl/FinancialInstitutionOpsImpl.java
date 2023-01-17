package com.neptunesoftware.nipintegrator.inward.repository.impl;

import com.neptunesoftware.nipintegrator.NIP.constants.NIPResponseCodes;
import com.neptunesoftware.nipintegrator.inward.model.financialinstitutionlist.FinancialInstitutionRequest;
import com.neptunesoftware.nipintegrator.inward.repository.FinancialInstitutionOps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.neptunesoftware.nipintegrator.inward.repository.SqlQueries.UPDATE_NIP_FINANCIAL_INSTITUTION;

@Repository
@Slf4j
@RequiredArgsConstructor
public class FinancialInstitutionOpsImpl implements FinancialInstitutionOps {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public String saveFinancialInstitutions(FinancialInstitutionRequest request) {
        jdbcTemplate.update(UPDATE_NIP_FINANCIAL_INSTITUTION, request.getRecord().get(0).getInstitutionCode(),
                request.getRecord().get(0).getInstitutionName(),
                request.getRecord().get(0).getCategory());

        return NIPResponseCodes.APPROVED_OR_COMPLETED_SUCCESSFULLY;
    }
}
