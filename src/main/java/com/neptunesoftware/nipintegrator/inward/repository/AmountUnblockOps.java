package com.neptunesoftware.nipintegrator.inward.repository;

import com.neptunesoftware.nipintegrator.inward.model.amountunblock.AmountUnblockRequest;

import java.math.BigDecimal;

public interface AmountUnblockOps {

    boolean isReferenceCodeValid(String referenceCode);

    BigDecimal getBlockedAmount(String referenceCode, String accountNumber);

    boolean isReferenceCodesUsed(String referenceCode);

    int partialAmountUnblock(AmountUnblockRequest amountUnblockRequest, BigDecimal amountLeft);

    int fullAmountUnblock(AmountUnblockRequest amountUnblockRequest);

    String doAmountUnBlock(AmountUnblockRequest request);
}
