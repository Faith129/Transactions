package com.neptunesoftware.nipintegrator.NIP.constants;

public class NIPResponseCodes {
	
	public static final String APPROVED_OR_COMPLETED_SUCCESSFULLY = "00";
	public static final String STATUS_UNKNOWN_PLEASE_WAIT_FOR_SETTLEMENT_REPORT = "01";
	public static final String INVALID_SENDER = "03";
	public static final String DO_NOT_HONOR = "05";
	public static final String DORMANT_ACCOUNT = "06";
	public static final String INVALID_ACCOUNT = "07";
	public static final String ACCOUNT_NAME_MISMATCH = "08";
	public static final String REQUEST_PROCESSING_IN_PROGRESS = "09";
	
	public static final String INVALID_TRANSACTION = "12";
	public static final String INVALID_AMOUNT = "13";
	public static final String INVALID_BATCH_NUMBER = "14";
	public static final String INVALID_SESSION_OR_RECORD_ID = "15";
	public static final String UNKNOWN_BANK_CODE = "16";
	public static final String INVALID_CHANNEL = "17";
	public static final String WRONG_METHOD_CALL = "18";
	
	public static final String NO_ACTION_TAKEN = "21";
	public static final String UNABLE_TO_LOCATE_RECORD = "25";
	public static final String DUPLICATE_RECORD = "26";
	
	public static final String FORMAT_ERROR = "30";
	public static final String SUSPECTED_FRAUD = "34";
	public static final String CONTACT_SENDING_BANK = "35";
	
	public static final String NO_SUFFICIENT_FUNDS = "51";
	public static final String TRANSACTION_NOT_PERMITTED_TO_SENDER = "57";
	public static final String TRANSACTION_NOT_PERMITTED_ON_CHANNEL = "58";
	
	public static final String TRANSFER_LIMIT_EXCEEDED = "61";
	public static final String SECURITY_VIOLATION = "63";
	public static final String EXCEEDS_WITHDRAWAL_FREQUENCY = "65";
	public static final String RESPONSE_RECEIVED_TOO_LATE = "68";
	public static final String UNSUCCESSFUL_ACCOUNT_OR_AMOUNT_BLOCK = "69";
	
	public static final String UNSUCCESSFUL_ACCOUNT_OR_AMOUNT_UNBLOCK = "70";
	public static final String EMPTY_MANDATE_REFERENCE_NUMBER = "71";
	
	public static final String BENEFICIARY_BANK_NOT_AVAILABLE = "91";
	public static final String ROUTING_ERROR = "92";
	public static final String DUPLICATE_TRANSACTION = "94";
	public static final String SYSTEM_MALFUNCTION = "96";
	public static final String TIMEOUT_WAITING_FOR_RESPONSE_FROM_DESTINATION = "97";
	
	
}
