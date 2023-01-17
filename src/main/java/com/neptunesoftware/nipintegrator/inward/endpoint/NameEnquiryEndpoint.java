package com.neptunesoftware.nipintegrator.inward.endpoint;

import com.neptunesoftware.nipintegrator.NIP.security.NIPCredential;
import com.neptunesoftware.nipintegrator.inward.client_api.nameenquirysingleitem.NameenquirysingleitemRequest;
import com.neptunesoftware.nipintegrator.inward.client_api.nameenquirysingleitem.NameenquirysingleitemResponse;
import com.neptunesoftware.nipintegrator.inward.model.nameenquiry.NESingleRequest;
import com.neptunesoftware.nipintegrator.inward.model.nameenquiry.NESingleResponse;
import com.neptunesoftware.nipintegrator.inward.services.NameEnquiryService;
import com.neptunesoftware.nipintegrator.utilities.Marshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import static com.neptunesoftware.nipintegrator.inward.endpoint.Encryptor.encryptor;

@Slf4j
@Endpoint
@RequiredArgsConstructor
public class NameEnquiryEndpoint {

    private static final String NAMESPACE_URI ="http://www.neptunesoftware.com/nipintegrator/inward/client_api/nameenquirysingleitem";
    private final NameEnquiryService nameEnquiryService;
    private final NIPCredential nipCredential;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "nameenquirysingleitemRequest")
    @ResponsePayload
    public NameenquirysingleitemResponse getNameFromAccount(@RequestPayload @NonNull NameenquirysingleitemRequest requestString) {

        log.info("Hitting the Name Enquiry endpoint!");
        System.out.println(requestString.getRequest());

        requestString.setRequest(
                encryptor.decryptFile(requestString.getRequest())
        );

        System.out.println(requestString.getRequest());
        NESingleRequest request = new NESingleRequest();
        request = Marshaller.convertXmlStringToObject(requestString.getRequest(), request);

        NESingleResponse neSingleResponse = nameEnquiryService.getAccountName(request);
        System.out.println("THE ACCOUNT NAME FOR THE ACCOUNT " + neSingleResponse.getAccountNumber() + " is " + neSingleResponse.getAccountName());
        NameenquirysingleitemResponse response = new NameenquirysingleitemResponse();
        response.setResponse(buildResponse(neSingleResponse));
        return response;
    }

    private String buildResponse(NESingleResponse response) {
        String responseString = Marshaller.convertObjectToXmlString(response);
        encryptor.setPublicKeyFileName(NIPCredential.getNibssPublicKey());
        return encryptor.encryptMessage(responseString);
    }
}
