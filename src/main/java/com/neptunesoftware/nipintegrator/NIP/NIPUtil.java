package com.neptunesoftware.nipintegrator.NIP;

import com.neptunesoftware.nipintegrator.NIP.security.NIPCredential;
import com.neptunesoftware.nipintegrator.utilities.CommonMethods;

public class NIPUtil {

	private static String sessionId;
	private static String batchNumber;
	private static String institutionCode;
	
	
	public static String getSessionID() {
		NIPCredential credential = new NIPCredential();
		String senderBankCode = credential.getBankCode();
		String dateAndTime = CommonMethods.getCurrentDateAsString("yyMMddHHmmss");
		String randomNumber = CommonMethods.random12Number();
		
		sessionId = senderBankCode + dateAndTime + randomNumber;
		
		return sessionId;
	}
	
	public static String getBatchNumber() {
		String senderBankCode = new NIPCredential().getBankCode();
		String dateAndTime = CommonMethods.getCurrentDateAsString("yyMMdd");
		String randomNumber = CommonMethods.random12Number();

		batchNumber = senderBankCode + dateAndTime + randomNumber;
		
		return batchNumber;
	}
	
	public static String getInstitutionCode() {
		
		return institutionCode;
	}
	
	public static boolean isInstitutionValid(String bankCode) {
		// select * financial_institution where code = 'bank code'
		
		return true;
	}
	
	public static String getRefinedRequest(String request) {
		/*
		 * */
		String beforeNarration = request.split("<Narration>")[0];
        String afterNarration = request.split("<Narration>")[1].split("</Narration>")[1];
        request = beforeNarration + "<Narration></Narration>" + afterNarration;
        
        return request;
	}
	
	public static String getNarration(String request) {
		/*
		 * */
        String narration = request.split("<Narration>")[1].split("</Narration>")[0];
        
        return narration;
	}
	
}
