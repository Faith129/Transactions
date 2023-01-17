package com.neptunesoftware.nipintegrator.inward.endpoint;

import com.neptunesoftware.nipintegrator.NIP.security.NIPCredential;
import com.neptunesoftware.nipintegrator.inward.model.accountunblock.AccountUnblockRequest;
import com.neptunesoftware.nipintegrator.inward.model.accountunblock.AccountUnblockResponse;
import com.neptunesoftware.nipintegrator.inward.services.AccountUnblockService;
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
@RequiredArgsConstructor
@Slf4j
public class AccountUnblockEndpoint {

    private static final String NAMESPACE_URI = "http://www.neptunesoftware.com/nipintegrator/inward/client_api/accountUnblockApi";
    private final NIPCredential nipCredential;

    private final AccountUnblockService accountUnblockService;

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AccountUnblockApiRequest")
    public String doUnblockAccount(@RequestPayload @NonNull final String requestString){

        log.info("=======Hitting the Account unblock endpoint!=======");
        String requestBody = encryptor.decryptFile(requestString);
        AccountUnblockRequest accountUnblockRequest = new AccountUnblockRequest();
        accountUnblockRequest = Marshaller.convertXmlStringToObject(requestBody, accountUnblockRequest);
        AccountUnblockResponse response = accountUnblockService.unblockAccount(accountUnblockRequest);

        return getAccountUnblockApiResponse(response);
    }

    private String getAccountUnblockApiResponse(AccountUnblockResponse response) {
        String responseString = Marshaller.convertObjectToXmlString(response);
        encryptor.setPublicKeyFileName(nipCredential.getNibssPublicKey());
        return encryptor.encryptMessage(responseString);
    }
}