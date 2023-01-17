package com.neptunesoftware.nipintegrator.inward.services;

import com.neptunesoftware.nipintegrator.inward.model.financialinstitutionlist.FinancialInstitutionRequest;
import com.neptunesoftware.nipintegrator.inward.model.financialinstitutionlist.FinancialInstitutionResponse;

public interface InstitutionListService {
    FinancialInstitutionResponse getInstitutionList(FinancialInstitutionRequest request);
}
