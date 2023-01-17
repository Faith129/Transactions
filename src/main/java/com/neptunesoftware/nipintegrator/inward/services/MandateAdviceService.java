package com.neptunesoftware.nipintegrator.inward.services;

import com.neptunesoftware.nipintegrator.inward.model.mandateadvice.MandateAdviceRequest;
import com.neptunesoftware.nipintegrator.inward.model.mandateadvice.MandateAdviceResponse;

public interface MandateAdviceService {
    MandateAdviceResponse getMandateAdvice(final MandateAdviceRequest request);

    boolean isMandateAdviceDuplicate(final String mandateReference);
}
