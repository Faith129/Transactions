package com.neptunesoftware.nipintegrator.inward.endpoint;

import com.neptunesoftware.nipintegrator.NIP.security.NIPCredential;
import com.neptunesoftware.nipintegrator.utilities.Marshaller;
import com.neptunesoftware.nipintegrator.inward.model.balanceenquiry.BalanceEnquiryRequest;
import com.neptunesoftware.nipintegrator.inward.model.balanceenquiry.BalanceEnquiryResponse;
import com.neptunesoftware.nipintegrator.inward.services.BalanceEnquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import static com.neptunesoftware.nipintegrator.inward.endpoint.Encryptor.encryptor;

@Endpoint
@RequiredArgsConstructor
public class BalanceEnquiryEndpoint {

    private final NIPCredential nipCredential;
    private static final String NAMESPACE_URI = "http://www.neptunesoftware.com/nipintegrator/inward/client_api/balanceEnquiryApi";
    private final BalanceEnquiryService balanceEnquiryService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "BalanceEnquiryApiRequest")
    @ResponsePayload
    public String getAccountBalance(@RequestPayload @NonNull String requestString){
        requestString = encryptor.decryptFile(requestString);
        BalanceEnquiryRequest request = new BalanceEnquiryRequest();
        request = Marshaller.convertXmlStringToObject(requestString, request);
        BalanceEnquiryResponse response = balanceEnquiryService.getBalance(request.getTargetAccountNumber());
        return getBalanceEnquiryApiResponse(response);
    }

    private String getBalanceEnquiryApiResponse(BalanceEnquiryResponse response) {
        String responseString = Marshaller.convertObjectToXmlString(response);
        encryptor.setPublicKeyFileName(nipCredential.getNibssPublicKey());
        return Encryptor.encryptor.encryptMessage(responseString);
    }

}
