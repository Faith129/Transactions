package com.neptunesoftware.nipintegrator.inward.repository;

import com.neptunesoftware.nipintegrator.inward.model.amountblock.AmountBlockRequest;

import java.math.BigDecimal;

public interface AmountBlockOps {
    boolean saveAmountBlock (AmountBlockRequest amountBlockRequest, String responseCode);
    boolean isAuthorizationValid(final String authCode);
    boolean isReferenceCodeUnique(String referenceCode);

    String performAmountBlock(AmountBlockRequest amountBlockRequest);
    int executeAmountBlock(AmountBlockRequest amountBlockRequest);
}
