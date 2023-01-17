package com.neptunesoftware.nipintegrator.outward.api;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@Builder
public class TransactionProperties {
    private String toAccountNumber;
    private String fromAccountNumber;
    private BigDecimal transactionAmount;
    private String transactionDescription;
    private String transactionReference;
    private boolean isReversal;
    private String debitCreditFlag;
}
