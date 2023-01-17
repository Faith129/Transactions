package com.neptunesoftware.nipintegrator.inward.services.impl;

import com.neptunesoftware.nipintegrator.NIP.constants.NIPResponseCodes;
import com.neptunesoftware.nipintegrator.inward.model.mandateadvice.MandateAdviceRequest;
import com.neptunesoftware.nipintegrator.inward.model.mandateadvice.MandateAdviceResponse;
import com.neptunesoftware.nipintegrator.inward.repository.MandateAdviceOps;
import com.neptunesoftware.nipintegrator.inward.repository.database_results.MandateAdvice;
import com.neptunesoftware.nipintegrator.inward.services.MandateAdviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MandateAdviceServiceImpl implements MandateAdviceService {

    private final MandateAdviceOps mandateAdviceOps;
    @Override
    public MandateAdviceResponse getMandateAdvice(final MandateAdviceRequest request) {

        MandateAdviceResponse response = new MandateAdviceResponse();

        if (isMandateAdviceDuplicate(request.getMandateReferenceNumber())){

            response.setSessionId(request.getSessionId());
            response.setResponseCode(NIPResponseCodes.APPROVED_OR_COMPLETED_SUCCESSFULLY);
            response.setAmount(request.getAmount());
            response.setBeneficiaryAccountName(request.getBeneficiaryAccountName());
            response.setBeneficiaryAccountNumber(request.getBeneficiaryAccountNumber());
            response.setBeneficiaryKYCLevel(request.getBeneficiaryKYCLevel());
            response.setBeneficiaryBankVerificationNumber(request.getBeneficiaryBankVerificationNumber());
            response.setDebitAccountName(request.getDebitAccountName());
            response.setDebitAccountNumber(request.getDebitAccountNumber());
            response.setDebitKYCLevel(request.getDebitKYCLevel());
            response.setDebitBankVerificationNumber(request.getDebitBankVerificationNumber());
            response.setChannelCode(request.getChannelCode());
            response.setDestinationInstitutionCode(request.getDestinationInstitutionCode());
            response.setMandateReferenceNumber(request.getMandateReferenceNumber());

            saveMandateAdvice(response);
        }
        return response;
    }

    @Override
    public boolean isMandateAdviceDuplicate(String mandateReference) {
        List<MandateAdvice> mandateAdviceList = mandateAdviceOps.queryMandateAdvice(mandateReference,
                NIPResponseCodes.APPROVED_OR_COMPLETED_SUCCESSFULLY);
        log.info("Size of values returned from database = {}. Mandate reference number is duplicate", mandateAdviceList.size());
        if (mandateAdviceList.size() != 0){
            throw new IllegalArgumentException("Mandate advice is duplicate");
        }
        return true;
    }

    private void saveMandateAdvice(MandateAdviceResponse response) {
        mandateAdviceOps.saveMandateReferenceNum(response);
    }
}
