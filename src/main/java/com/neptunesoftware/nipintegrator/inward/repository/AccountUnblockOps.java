package com.neptunesoftware.nipintegrator.inward.repository;

import com.neptunesoftware.nipintegrator.inward.repository.database_results.NIPAccountBlock;

import java.util.List;

public interface AccountUnblockOps {

    String doAccountUnblock(String accountNumber);

    List<NIPAccountBlock> queryUsedReferenceCode(String referenceCode);

    List<NIPAccountBlock> referenceCodeValidation(String referenceCode, String accountNumber);

    int updateNIPAccountUnblock(String referenceCode, String accountNumber);
}
