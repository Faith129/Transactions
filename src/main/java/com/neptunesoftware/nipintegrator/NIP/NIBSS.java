package com.neptunesoftware.nipintegrator.NIP;

import com.neptunesoftware.nipintegrator.NIP.security.NIPCredential;
import com.neptunesoftware.nipintegrator.NIP.security.SSMLib;
import com.neptunesoftware.nipintegrator.outward.api.NIBSSResponse;
import com.neptunesoftware.nipintegrator.outward.api.fundtransferdirectcreditoutward.FundTransferDirectCreditRequest;
import com.neptunesoftware.nipintegrator.outward.api.fundtransferdirectcreditoutward.FundTransferDirectCreditResponse;
import com.neptunesoftware.nipintegrator.outward.api.fundtransferdirectcreditoutward.NibssFundTransferResponse;
import com.neptunesoftware.nipintegrator.outward.api.nameenquiryoutward.NameEnquiryRequest;
import com.neptunesoftware.nipintegrator.outward.api.nameenquiryoutward.NameEnquiryResponse;
import com.neptunesoftware.nipintegrator.outward.api.nameenquiryoutward.NibssNameEnquiryResponse;
import com.neptunesoftware.nipintegrator.outward.api.tsq.NibssTSQResponse;
import com.neptunesoftware.nipintegrator.outward.api.tsq.TransactionStatusQueryRequest;
import com.neptunesoftware.nipintegrator.outward.api.tsq.TransactionStatusQueryResponse;
import com.neptunesoftware.nipintegrator.utilities.CommonMethods;
import com.neptunesoftware.nipintegrator.utilities.Marshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.soap.*;

@Service
@RequiredArgsConstructor
public class NIBSS {

    private final NIPCredential nipCredential;
    private final SoapWebserviceCall soapWebserviceCall;

    public NameEnquiryResponse nameEnquirySingleItem(String destinationInstitutionCode, int channelCode, String accountNumber){
        NameEnquiryRequest request = buildNameEnquiryRequest(destinationInstitutionCode, channelCode, accountNumber);

        String requestString = Marshaller.convertObjectToXmlString(request);

        // instantiate the NIBSS encryptor/decryptor
        SSMLib encryptor = getSsmLib();
        // encrypt request
        encryptor.setPublicKeyFileName(NIPCredential.getNibssPublicKey());
        String encryptedRequest = encryptor.encryptMessage(requestString);

        System.out.println("Encrypted Request:\n\r" + encryptedRequest);

        String encryptedResponse = "";
        try {
            encryptedResponse = SoapWebserviceCall.soapWebService(NIPCredential.getWsdlUrl(), createSoapRequest("nameEnquirySingleItem", encryptedRequest));
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Could not invoke nameenquirysingleitem method!" + e.getMessage());
        }

        encryptedResponse = getNIBSSResponse(encryptedResponse, NibssNameEnquiryResponse.class);
        System.out.println("Encrypted Response:\n\r" + encryptedResponse);
        // decrypt NIBSS response using our public key
      //  encryptor.setPublicKeyFileName(nipCredential.getPublicKey());
        String decryptedResponse = encryptor.decryptFile(encryptedResponse);

        // unmarshall NIBSS response
        NameEnquiryResponse nameEnquiryResponse = new NameEnquiryResponse();
        try {
            nameEnquiryResponse = Marshaller.convertXmlStringToObject(decryptedResponse, nameEnquiryResponse);
        } catch (Exception e) {
            System.out.println("Could not unmarshall nameenquirysingleitem NIBSS response!");
        }
        return nameEnquiryResponse;
    }

    public FundTransferDirectCreditResponse fundTransferDirectCreditSingleItem (FundTransferDirectCreditRequest request){

        String requestString = Marshaller.convertObjectToXmlString(request);
        SSMLib encryptor = getSsmLib();
        // encrypt request
        encryptor.setPublicKeyFileName(NIPCredential.getNibssPublicKey());
        String encryptedRequest = encryptor.encryptMessage(requestString);
        System.out.println("Encrypted Request:\n\r" + encryptedRequest);

        String encryptedResponse = "";
        try {
            encryptedResponse = SoapWebserviceCall.soapWebService(NIPCredential.getWsdlUrl(), createSoapRequest("fundTransferDirectCreditSingleItem", encryptedRequest));
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Could not invoke fundtransfersingleitem_dc method!");
        }

        // extract response from response object
        encryptedResponse = getNIBSSResponse(encryptedResponse, NibssFundTransferResponse.class);
        //encryptor.setPublicKeyFileName(nipCredential.getPublicKey());
        String decryptedResponse = encryptor.decryptFile(encryptedResponse);

        // unmarshall NIBSS response
        FundTransferDirectCreditResponse fundTransferDCResponse = new FundTransferDirectCreditResponse();
        try {
            fundTransferDCResponse = Marshaller.convertXmlStringToObject(decryptedResponse, fundTransferDCResponse);
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Could not unmarshall fundTransferDirectCreditSingleItem  NIBSS response!");
        }

        return fundTransferDCResponse;
    }

    public TransactionStatusQueryResponse transactionStatusQuerySingleItem(String sessionId, int channelCode){

        String encryptedResponse;
        TransactionStatusQueryRequest request = buildTransactionStatusQueryRequest(sessionId, channelCode);
        String requestString = Marshaller.convertObjectToXmlString(request);
        SSMLib encryptor = getSsmLib();
        encryptor.setPublicKeyFileName(NIPCredential.getNibssPublicKey());
        String encryptedRequest = encryptor.encryptMessage(requestString);

        System.out.println("ENCRYPTED REQUEST " + encryptedRequest);

        try {
             encryptedResponse = SoapWebserviceCall.soapWebService(NIPCredential.getWsdlUrl(), createSoapRequest("transactionStatusQuerySingleItem", encryptedRequest));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // extract response from response object
        encryptedResponse = getNIBSSResponse(encryptedResponse, NibssTSQResponse.class);
        // decrypt NIBSS response using our public key
        //encryptor.setPublicKeyFileName(nipCredential.getPublicKey());
        String decryptedResponse = encryptor.decryptFile(encryptedResponse);
        TransactionStatusQueryResponse transactionStatusQueryResponse = new TransactionStatusQueryResponse();

        try {
            transactionStatusQueryResponse = CommonMethods.xmlStringToObject(decryptedResponse, TransactionStatusQueryResponse.class);
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Could not unmarshall txnstatusquerysingleitem NIBSS response!");
        }
        return transactionStatusQueryResponse;
    }

    private TransactionStatusQueryRequest buildTransactionStatusQueryRequest(String sessionId, int channelCode) {
        TransactionStatusQueryRequest request = new TransactionStatusQueryRequest();
        request.setSessionId(sessionId);
        request.setChannelCode(channelCode);
        request.setSourceInstitutionCode(NIPCredential.getBankCode());
        return request;
    }

    private NameEnquiryRequest buildNameEnquiryRequest(String destinationInstitutionCode, int channelCode, String accountNumber) {
        NameEnquiryRequest request = new NameEnquiryRequest();
        request.setSessionId(NIPUtil.getSessionID());
        request.setAccountNumber(accountNumber);
        request.setChannelCode(channelCode);
        request.setDestinationInstitutionCode(destinationInstitutionCode);
        return request;
    }

    public SSMLib getSsmLib() {
        return new SSMLib()
                .setUsername(NIPCredential.getUsername()).setPassword(NIPCredential.getPassword());
    }

    private String getNIBSSResponse(final String nibssResponse, Class<?> clazz ) {

        // remove XML header from string
        String response = nibssResponse.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();

        // remove namespace
        //response = response.replaceAll("xmlns:ns2=\"http://core.nip.nibss/\"", "").trim();

        NIBSSResponse nibssResponseObject = new NIBSSResponse();
        try {
            nibssResponseObject = (NIBSSResponse) CommonMethods.xmlStringToObject(response, clazz);
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Could not Unmarshall response from returned object!" + e.getMessage());
        }

        return nibssResponseObject.getResponse();
    }

    private SOAPMessage createSoapRequest(String methodName, String encryptedRequest) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);

        // creates SOAP message
        SOAPMessage soapMessage = messageFactory.createMessage();

        // get the soap part of the SOAP message
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // creates the ENVELOPE root element
        SOAPEnvelope soapEnvelope = soapPart.getEnvelope();

        // adds namespace to the root element
        soapEnvelope.addNamespaceDeclaration("core", "http://core.nip.nibss/");

        // gets the body part of the soap message
        SOAPBody soapBody = soapEnvelope.getBody();

        // adds the nameenquirysingleitem node
        SOAPElement methodNameElement = soapBody.addChildElement(methodName, "core");

        // adds the request node
        SOAPElement requestElement = methodNameElement.addChildElement("request");
        // adds the request
        requestElement.addTextNode(encryptedRequest);

        // save changes
        soapMessage.saveChanges();

        // return created request object
        return soapMessage;
    }
}
