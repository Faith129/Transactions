package com.neptunesoftware.nipintegrator;

import com.neptunesoftware.nipintegrator.NIP.NIBSS;
import com.neptunesoftware.nipintegrator.NIP.security.NIPCredential;
import com.neptunesoftware.nipintegrator.inward.client_api.nameenquirysingleitem.NameenquirysingleitemRequest;
import com.neptunesoftware.nipintegrator.inward.endpoint.Encryptor;
import com.neptunesoftware.nipintegrator.inward.model.nameenquiry.NESingleRequest;
import com.neptunesoftware.nipintegrator.utilities.Marshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class NipintegratorApplication implements CommandLineRunner {

	//TODO Exception handling

	private final NIBSS nibss;
	static ClassLoader classLoader = NIPCredential.class.getClassLoader();

	private final NIPCredential nipCredential;


	public static void main(String[] args) {
		SpringApplication.run(NipintegratorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		String encryptMessage = Encryptor.encryptor.encryptMessage("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>  \n" +
//				"    <NESingleRequest> \n" +
//				"  <SessionId></SessionId> \n" +
//				"  <DestinationInstitutionCode>000013</DestinationInstitutionCode> \n" +
//				"  <ChannelCode>1</ChannelCode> \n" +
//				"  <AccountNumber>0013000005</AccountNumber> \n" +
//				"   </NESingleRequest>");
//
//		System.out.println(encryptMessage);
//
//		String decryptFile = Encryptor.encryptor.decryptFile(encryptMessage);
//
//		System.out.println(decryptFile);
//
//		System.out.println(Marshaller.convertXmlStringToObject(decryptFile, neSingleRequest).getAccountNumber());
//		System.out.println(Marshaller.convertObjectToXmlString(neSingleRequest));
	}
}
