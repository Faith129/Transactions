package com.neptunesoftware.nipintegrator.inward.services.impl;

import com.neptunesoftware.nipintegrator.inward.model.financialinstitutionlist.FinancialInstitutionRequest;
import com.neptunesoftware.nipintegrator.inward.model.financialinstitutionlist.FinancialInstitutionResponse;
import com.neptunesoftware.nipintegrator.inward.repository.FinancialInstitutionOps;
import com.neptunesoftware.nipintegrator.inward.services.InstitutionListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InstitutionServiceImpl implements InstitutionListService {

    private final FinancialInstitutionOps financialInstitutionOps;

    @Override
    public FinancialInstitutionResponse getInstitutionList(FinancialInstitutionRequest request) {
        FinancialInstitutionResponse response = new FinancialInstitutionResponse();
        String responseCode = financialInstitutionOps.saveFinancialInstitutions(request);

        response.setDestinationInstitutionCode(request.getRecord().get(0).getInstitutionCode());
        response.setResponseCode(responseCode);
        response.setNumberOfRecords(request.getRecord().size());
        response.setChannelCode(request.getHeader().getChannelCode());
        response.setBatchNumber(request.getHeader().getBatchNumber());
        return response;
    }
}
