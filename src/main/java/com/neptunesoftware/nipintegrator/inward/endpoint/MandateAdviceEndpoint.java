package com.neptunesoftware.nipintegrator.inward.endpoint;

import com.neptunesoftware.nipintegrator.NIP.security.NIPCredential;
import com.neptunesoftware.nipintegrator.utilities.Marshaller;
import com.neptunesoftware.nipintegrator.inward.model.mandateadvice.MandateAdviceRequest;
import com.neptunesoftware.nipintegrator.inward.model.mandateadvice.MandateAdviceResponse;
import com.neptunesoftware.nipintegrator.inward.services.MandateAdviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import static com.neptunesoftware.nipintegrator.inward.endpoint.Encryptor.encryptor;

@Endpoint
@RequiredArgsConstructor
public class MandateAdviceEndpoint {
    private static final String NAMESPACE_URI = "http://www.neptunesoftware.com/nipintegrator/inward/client_api/mandateAdviceApi";
    private final MandateAdviceService mandateAdviceService;
    private final NIPCredential nipCredential;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "MandateAdviceApiRequest")
    @ResponsePayload
    public String saveMandateAdvice(@RequestPayload @NonNull String requestString){

        System.out.println(requestString);
        requestString = encryptor.decryptFile(requestString);
        MandateAdviceRequest request = new MandateAdviceRequest();
        request = Marshaller.convertXmlStringToObject(requestString, request);
        MandateAdviceResponse response = mandateAdviceService.getMandateAdvice(request);
        return getMandateAdviceApiResponse(response);
    }

    private String getMandateAdviceApiResponse(MandateAdviceResponse response) {
        String responseString = Marshaller.convertObjectToXmlString(response);
        encryptor.setPublicKeyFileName(nipCredential.getNibssPublicKey());
        return encryptor.encryptMessage(responseString);
    }
}
