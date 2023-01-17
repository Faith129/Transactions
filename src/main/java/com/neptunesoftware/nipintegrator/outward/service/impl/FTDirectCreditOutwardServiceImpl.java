package com.neptunesoftware.nipintegrator.outward.service.impl;

import com.neptunesoftware.nipintegrator.NIP.NIBSS;
import com.neptunesoftware.nipintegrator.NIP.NIPUtil;
import com.neptunesoftware.nipintegrator.outward.api.NIPRequestType;
import com.neptunesoftware.nipintegrator.outward.api.fundtransferdirectcreditoutward.FundTransferDirectCreditRequest;
import com.neptunesoftware.nipintegrator.outward.api.fundtransferdirectcreditoutward.FundTransferDirectCreditResponse;
import com.neptunesoftware.nipintegrator.outward.repository.impl.FundTransferDirectCreditOutwardServiceDBOperation;
import com.neptunesoftware.nipintegrator.outward.service.FTDirectCreditOutwardService;
import com.neptunesoftware.nipintegrator.outward.service.TSQOutwardService;
import com.neptunesoftware.nipintegrator.utilities.Marshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class FTDirectCreditOutwardServiceImpl implements FTDirectCreditOutwardService {

    public static final int TIME_DELAY_IN_MILLI_SECONDS = 60_000;
    private final NIBSS nibss;
    private final TSQOutwardService tsqOutwardService;
    private final FundTransferDirectCreditOutwardServiceDBOperation dbOperation;

    @Override
    public FundTransferDirectCreditResponse performFundTransferDirectCredit(FundTransferDirectCreditRequest request) {

        request.setSessionId(NIPUtil.getSessionID());
        FundTransferDirectCreditResponse response = nibss.fundTransferDirectCreditSingleItem(request);

        Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    Thread.sleep(TIME_DELAY_IN_MILLI_SECONDS);
                    tsqOutwardService.transactionStatusQuery(response, NIPRequestType.OUTWARD);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
        });
        return response;
    }

    @Override
    public boolean savedFundTransfer(FundTransferDirectCreditResponse response, NIPRequestType inward) {
        return dbOperation.hasSavedFundTransfer(response, inward);
    }
}
