package com.neptunesoftware.nipintegrator.inward.endpoint;

import com.neptunesoftware.nipintegrator.NIP.security.NIPCredential;
import com.neptunesoftware.nipintegrator.inward.model.financialinstitutionlist.FinancialInstitutionRequest;
import com.neptunesoftware.nipintegrator.inward.model.financialinstitutionlist.FinancialInstitutionResponse;
import com.neptunesoftware.nipintegrator.inward.services.InstitutionListService;
import com.neptunesoftware.nipintegrator.utilities.Marshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import static com.neptunesoftware.nipintegrator.inward.endpoint.Encryptor.encryptor;

@Endpoint
@Slf4j
@RequiredArgsConstructor
public class FinancialInstitutionListEndpoint {

    private final NIPCredential nipCredential;
    private final InstitutionListService institutionListService;
   // private static final String NAMESPACE_URI = "http://www.neptunesoftware.com/nipintegrator/inward/controller/financialInstitutionList";

    private static final String NAMESPACE_URI = "http://www.neptunesoftware.com/nipintegrator/inward/client_api/financialInstitutionListApi";


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "FinancialInstitutionListApiRequest")
    @ResponsePayload
    public String getFinancialInstitutionList(@RequestPayload @NonNull String requestString){
        log.info("Hitting the Financial Institution LIst endpoint.");
        requestString = encryptor.decryptFile(requestString);
        FinancialInstitutionRequest request = new FinancialInstitutionRequest();
        request = Marshaller.convertXmlStringToObject(requestString, request);
        FinancialInstitutionResponse response = institutionListService.getInstitutionList(request);
        String responseString =  Marshaller.convertObjectToXmlString(response);
        return getFinancialInstitutionListApiResponse(responseString);
    }

    private String getFinancialInstitutionListApiResponse(String responseString) {
        encryptor.setPublicKeyFileName(nipCredential.getNibssPublicKey());
        return encryptor.encryptMessage(responseString);
    }

}
