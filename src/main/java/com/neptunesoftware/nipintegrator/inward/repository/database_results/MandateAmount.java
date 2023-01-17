package com.neptunesoftware.nipintegrator.inward.repository.database_results;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class MandateAmount {
    private BigDecimal mandateAmount;
    private String mandateReference;
}
