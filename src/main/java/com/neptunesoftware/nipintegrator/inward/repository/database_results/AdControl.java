package com.neptunesoftware.nipintegrator.inward.repository.database_results;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AdControl {
private String AVAIL_BAL;
private String COLL_BAL;
private String POST_BAL;
}
