package com.neptunesoftware.nipintegrator.inward.repository.database_results;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NameEnquiry {
    private String accountName;
    private String bvn;
    private String title;
}
