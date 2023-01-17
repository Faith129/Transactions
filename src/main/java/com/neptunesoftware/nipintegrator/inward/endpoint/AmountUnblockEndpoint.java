package com.neptunesoftware.nipintegrator.inward.endpoint;

import com.neptunesoftware.nipintegrator.NIP.security.NIPCredential;
import com.neptunesoftware.nipintegrator.utilities.Marshaller;
import com.neptunesoftware.nipintegrator.inward.model.amountunblock.AmountUnblockRequest;
import com.neptunesoftware.nipintegrator.inward.model.amountunblock.AmountUnblockResponse;
import com.neptunesoftware.nipintegrator.inward.services.AmountUnblockService;
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
public class AmountUnblockEndpoint {

    private final NIPCredential nipCredential;
    private final AmountUnblockService amountUnblockService;
    private static final String NAMESPACE_URI = "http://www.neptunesoftware.com/nipintegrator/inward/client_api/amountUnblockApi";

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AmountUnblockApiRequest")
    public String unblockAmount(@RequestPayload @NonNull String requestString){

        requestString = encryptor.decryptFile(requestString);
        log.info("Hitting the amount unblock endpoint with request");
        AmountUnblockRequest request = new AmountUnblockRequest();
        request = Marshaller.convertXmlStringToObject(requestString, request);
        AmountUnblockResponse response = amountUnblockService.doAmountUnblock(request);

        return getAmountUnblockApiResponse(response);
    }

    private String getAmountUnblockApiResponse(AmountUnblockResponse response) {
        String responseString = Marshaller.convertObjectToXmlString(response);
        encryptor.setPublicKeyFileName(nipCredential.getNibssPublicKey());

        return encryptor.encryptMessage(responseString);
    }

}