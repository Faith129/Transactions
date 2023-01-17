package com.neptunesoftware.nipintegrator.inward.endpoint;

import com.neptunesoftware.nipintegrator.NIP.security.NIPCredential;
import com.neptunesoftware.nipintegrator.utilities.Marshaller;
import com.neptunesoftware.nipintegrator.inward.model.fundtransferdirectdebit.FundTransferDirectDebitRequest;
import com.neptunesoftware.nipintegrator.inward.model.fundtransferdirectdebit.FundTransferDirectDebitResponse;
import com.neptunesoftware.nipintegrator.inward.services.FundTransferDirectDebitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import static com.neptunesoftware.nipintegrator.inward.endpoint.Encryptor.encryptor;

@Endpoint
@RequiredArgsConstructor
@Slf4j
public class FundTransferDirectDebitEndpoint {

    private final FundTransferDirectDebitService fundTransferDirectDebitService;
    private final NIPCredential nipCredential;
    private static final String NAMESPACE_URI = "http://www.neptunesoftware.com/nipintegrator/inward/client_api/fundTransferDirectDebitApi";

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "FundTransferDirectDebitApiRequest")
    public String FTDirectDebitEntryPoint(@RequestPayload @NonNull String requestString){
        log.info("Hitting the Fund Transfer direct debit endpoint");
        System.out.println(requestString);
        requestString = encryptor.decryptFile(requestString);
        FundTransferDirectDebitRequest request = new FundTransferDirectDebitRequest();
        request = Marshaller.convertXmlStringToObject(requestString, request);
        FundTransferDirectDebitResponse response =  fundTransferDirectDebitService.doFundTransferDirectDebit(request);
        System.out.println(response.getResponseCode());
        return getFundTransferDirectCreditApiResponse(response);
    }

    private String getFundTransferDirectCreditApiResponse(FundTransferDirectDebitResponse response) {
        String responseString = Marshaller.convertObjectToXmlString(response);
        encryptor.setPublicKeyFileName(nipCredential.getNibssPublicKey());
        return encryptor.encryptMessage(responseString);
    }
}
