package com.neptunesoftware.nipintegrator.inward.repository.database_results;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PcPTID {
    String tableName;
    int ptid;
}
