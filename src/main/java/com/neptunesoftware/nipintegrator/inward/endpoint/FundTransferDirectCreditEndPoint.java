package com.neptunesoftware.nipintegrator.inward.endpoint;

import com.neptunesoftware.nipintegrator.NIP.security.NIPCredential;
import com.neptunesoftware.nipintegrator.utilities.Marshaller;
import com.neptunesoftware.nipintegrator.inward.model.fundtransferdirectcredit.FundTransferDirectCreditRequest;
import com.neptunesoftware.nipintegrator.inward.services.FTDirectCreditService;
import com.neptunesoftware.nipintegrator.outward.api.fundtransferdirectcreditoutward.FundTransferDirectCreditResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import static com.neptunesoftware.nipintegrator.inward.endpoint.Encryptor.encryptor;

@Endpoint
@RequiredArgsConstructor
public class FundTransferDirectCreditEndPoint {

    private static final String NAMESPACE = "http://www.neptunesoftware.com/nipintegrator/inward/client_api/fundTransferDirectCreditApi";
    private final FTDirectCreditService directCreditService;
    private final NIPCredential nipCredential;

    @PayloadRoot(namespace = NAMESPACE, localPart = "FundTransferDirectCreditApiRequest")
    @ResponsePayload
    public String fundTransferDirectCreditOperation(
            @RequestPayload @NonNull String requestString) {

        requestString = encryptor.decryptFile(requestString);
        FundTransferDirectCreditRequest request = new FundTransferDirectCreditRequest();
        request = Marshaller.convertXmlStringToObject(requestString, request);
        FundTransferDirectCreditResponse response =  directCreditService.executeFundTransfer(request);
        return getFundTransferDirectCreditApiResponse(response);
    }

    private String getFundTransferDirectCreditApiResponse(FundTransferDirectCreditResponse response) {
        String responseString = Marshaller.convertObjectToXmlString(response);
        encryptor.setPublicKeyFileName(nipCredential.getNibssPublicKey());
        return encryptor.encryptMessage(responseString);
    }

}
