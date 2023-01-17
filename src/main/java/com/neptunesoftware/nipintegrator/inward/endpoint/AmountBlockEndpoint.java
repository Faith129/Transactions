package com.neptunesoftware.nipintegrator.inward.endpoint;

import com.neptunesoftware.nipintegrator.NIP.security.NIPCredential;
import com.neptunesoftware.nipintegrator.inward.model.amountblock.AmountBlockRequest;
import com.neptunesoftware.nipintegrator.inward.model.amountblock.AmountBlockResponse;
import com.neptunesoftware.nipintegrator.inward.services.AmountBlockService;
import com.neptunesoftware.nipintegrator.utilities.Marshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import static com.neptunesoftware.nipintegrator.inward.endpoint.Encryptor.encryptor;

@Endpoint
@RequiredArgsConstructor
public class AmountBlockEndpoint {
    private static final String NAMESPACE_URI = "http://www.neptunesoftware.com/nipintegrator/inward/client_api/amountBlockApi";
    private final AmountBlockService amountBlockService;
    private final NIPCredential nipCredential;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AmountBlockApiRequest")
    @ResponsePayload
    public String getAmountBlock(@RequestPayload @NonNull final String requestString){

        String requestBody = encryptor.decryptFile(requestString);
        AmountBlockRequest amountBlockRequest = new AmountBlockRequest();
        amountBlockRequest = Marshaller.convertXmlStringToObject(requestBody, amountBlockRequest);
        AmountBlockResponse response = amountBlockService.getAmountBlock(amountBlockRequest);
        return getAmountBlockApiResponse(response);
    }

    private String getAmountBlockApiResponse(AmountBlockResponse response) {
        String responseString = Marshaller.convertObjectToXmlString(response);
        encryptor.setPublicKeyFileName(nipCredential.getNibssPublicKey());
        return encryptor.encryptMessage(responseString);
    }
}
