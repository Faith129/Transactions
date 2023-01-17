package com.neptunesoftware.nipintegrator.inward.repository;

import com.neptunesoftware.nipintegrator.inward.model.mandateadvice.MandateAdviceResponse;
import com.neptunesoftware.nipintegrator.inward.repository.database_results.MandateAdvice;

import java.util.List;

public interface MandateAdviceOps {
    
    int saveMandateReferenceNum(MandateAdviceResponse response);

    List<MandateAdvice> queryMandateAdvice(String mandateReference, String approvedOrCompletedSuccessfully);
}
