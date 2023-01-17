package com.neptunesoftware.nipintegrator.inward.endpoint;


import com.neptunesoftware.nipintegrator.NIP.security.NIPCredential;
import com.neptunesoftware.nipintegrator.utilities.Marshaller;
import com.neptunesoftware.nipintegrator.inward.model.fundtransferadvicedirectcredit.FundTransferAdviceDirectCreditRequest;
import com.neptunesoftware.nipintegrator.inward.model.fundtransferadvicedirectcredit.FundTransferAdviceDirectCreditResponse;
import com.neptunesoftware.nipintegrator.inward.services.FTAdviceDirectCreditService;
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
public class FundTransferAdviceDirectCreditEndpoint {

  private final NIPCredential nipCredential;
  private final FTAdviceDirectCreditService FTAdviceDirectCreditService;
  private static final String NAMESPACE_URI =
      "http://www.neptunesoftware.com/nipintegrator/inward/client_api/fundTransferAdviceDirectCreditApi";

  @ResponsePayload
  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "FundTransferAdviceDirectCreditApiRequest")
  public String FundTransferAdviceDirectCreditEntryPoint( @RequestPayload @NonNull String requestString) {

    log.info("Hitting the Fund Transfer Advice Direct Credit");
    requestString = encryptor.decryptFile(requestString);
    FundTransferAdviceDirectCreditRequest request = new FundTransferAdviceDirectCreditRequest();
    request = Marshaller.convertXmlStringToObject(requestString, request);
    FundTransferAdviceDirectCreditResponse response = FTAdviceDirectCreditService.doFundTransferAdviceDirectCredit(request);

    return getFundTransferAdviceDirectCreditApiResponse(response);
  }

  private String getFundTransferAdviceDirectCreditApiResponse(FundTransferAdviceDirectCreditResponse response) {
    String responseString = Marshaller.convertObjectToXmlString(response);
    encryptor.setPublicKeyFileName(NIPCredential.getNibssPublicKey());
    return encryptor.encryptMessage(responseString);
  }
}