package com.neptunesoftware.nipintegrator.inward.endpoint;

import com.neptunesoftware.nipintegrator.NIP.security.NIPCredential;
import com.neptunesoftware.nipintegrator.inward.model.accountblock.AccountBlockRequest;
import com.neptunesoftware.nipintegrator.inward.model.accountblock.AccountBlockResponse;
import com.neptunesoftware.nipintegrator.inward.services.AccountBlockService;
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
public class AccountBlockEndPoint {
    private static final String NAMESPACE = "http://www.neptunesoftware.com/nipintegrator/inward/client_api/accountBlockApi";
    private final NIPCredential nipCredential;
    private final AccountBlockService service;

    @PayloadRoot(namespace = NAMESPACE, localPart = "AccountBlockApiRequest")
    @ResponsePayload
    public String blockAccount(@RequestPayload @NonNull final String requestString) {

        String requestBody = encryptor.decryptFile(requestString);

        AccountBlockRequest accountBlockRequest = new AccountBlockRequest();
        accountBlockRequest = Marshaller.convertXmlStringToObject(requestBody, accountBlockRequest);
        AccountBlockResponse response = service.accountBlock(accountBlockRequest);

        return getAccountBlockApiResponse(response);
    }

    private String getAccountBlockApiResponse(AccountBlockResponse response) {
        String responseString = Marshaller.convertObjectToXmlString(response);
        encryptor.setPublicKeyFileName(nipCredential.getNibssPublicKey());
        return encryptor.encryptMessage(responseString);
    }

}
