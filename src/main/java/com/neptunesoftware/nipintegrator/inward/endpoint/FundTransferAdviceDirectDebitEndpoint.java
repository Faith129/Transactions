package com.neptunesoftware.nipintegrator.inward.endpoint;

import com.neptunesoftware.nipintegrator.NIP.security.NIPCredential;
import com.neptunesoftware.nipintegrator.utilities.Marshaller;
import com.neptunesoftware.nipintegrator.inward.model.fundtransferadvicedirectdebit.FundTransferAdviceDirectDebitRequest;
import com.neptunesoftware.nipintegrator.inward.model.fundtransferadvicedirectdebit.FundTransferAdviceDirectDebitResponse;
import com.neptunesoftware.nipintegrator.inward.services.FundTransferAdviceDirectDebitService;
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
public class FundTransferAdviceDirectDebitEndpoint {
    private static final String NAMESPACE_URI = "http://www.neptunesoftware.com/nipintegrator/inward/client_api/fundTransferAdviceDirectDebitApi";

    private final NIPCredential nipCredential;
    private final FundTransferAdviceDirectDebitService fundTransferAdviceDirectDebitService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "FundTransferAdviceDirectDebitApiRequest")
    @ResponsePayload
    public String getFundTransferAdviceDirectDebit(@RequestPayload @NonNull String requestString){
        System.out.println(requestString);
        requestString = encryptor.decryptFile(requestString);
        FundTransferAdviceDirectDebitRequest request = new FundTransferAdviceDirectDebitRequest();
        request = Marshaller.convertXmlStringToObject(requestString, request);
        FundTransferAdviceDirectDebitResponse response =  fundTransferAdviceDirectDebitService.getFundTransferAdviceDircetDebit(request);
        return getFundTransferAdviceDirectDebitApiResponse(response);
    }

    private String getFundTransferAdviceDirectDebitApiResponse(FundTransferAdviceDirectDebitResponse response) {
        String responseString = Marshaller.convertObjectToXmlString(response);
        encryptor.setPublicKeyFileName(nipCredential.getNibssPublicKey());
        return encryptor.encryptMessage(responseString);
    }

}
