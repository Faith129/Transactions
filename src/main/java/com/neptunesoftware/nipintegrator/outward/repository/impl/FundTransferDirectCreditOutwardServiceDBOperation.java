package com.neptunesoftware.nipintegrator.outward.repository.impl;

import com.neptunesoftware.nipintegrator.utilities.CommonMethods;
import com.neptunesoftware.nipintegrator.outward.api.NIPRequestType;
import com.neptunesoftware.nipintegrator.outward.api.fundtransferdirectcreditoutward.FundTransferDirectCreditResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class FundTransferDirectCreditOutwardServiceDBOperation{
	private final JdbcTemplate template;

	public boolean hasSavedFundTransfer(FundTransferDirectCreditResponse fdr, NIPRequestType requestType) {
		CommonMethods.logContent("To save records");
		if (fdr.getBeneficiaryBankVerificationNumber().isEmpty()) {
			fdr.setBeneficiaryBankVerificationNumber("12345678945");
		}

		if(fdr.getOriginatorBankVerificationNumber().isEmpty()) {
			fdr.setOriginatorBankVerificationNumber("12345678945");
		}

		if(fdr.getOriginatorKYCLevel()  == 0) {
			fdr.setOriginatorKYCLevel(1);
		}

		if(fdr.getTransactionLocation().isEmpty()) {
			fdr.setTransactionLocation("11111111111");
		}

		if(fdr.getNameEnquiryRef().isEmpty()) {
			fdr.setNameEnquiryRef(fdr.getSessionId());
		}

		if(fdr.getNarration().isEmpty()) {
			fdr.setNarration("NIP Transfer " + fdr.getSessionId());
		}

		CommonMethods.logContent("Record to be saved: \n" + fdr);
		CommonMethods.logContent("Record to be saved: \n" + CommonMethods.objectToXml(fdr));
		System.out.println("Save Successful Records For TSQ");

		String serviceType = requestType == NIPRequestType.INWARD ? "INWARD" : "OUTWARD";
		
		String sql = "insert into nip_xfer_direct_credit(session_id, name_enquiry_ref, destination_institution_code," +
				" channel_code, ben_account_name, ben_account_number, ben_bvn, ben_kyclevel, ori_account_name, " +
				" ori_account_number, ori_bvn, ori_kyclevel, transaction_location, narration, payment_reference," +
				" amount, response_code, service_type) \r\n" + 
				"values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


		int result = template.update(sql, 	fdr.getSessionId(),
											fdr.getNameEnquiryRef().isEmpty() ? "1" : fdr.getNameEnquiryRef(),
											fdr.getDestinationInstitutionCode(),
											fdr.getChannelCode(),
											fdr.getBeneficiaryAccountName(),
											fdr.getBeneficiaryAccountNumber(),
											fdr.getBeneficiaryBankVerificationNumber().isEmpty()
													? "11111111111" : fdr.getBeneficiaryBankVerificationNumber(),
											fdr.getBeneficiaryKYCLevel() == 0 ? 1 : fdr.getBeneficiaryKYCLevel(),
											fdr.getOriginatorAccountName(),
											fdr.getOriginatorAccountNumber(),
											fdr.getOriginatorBankVerificationNumber().isEmpty()
													? "11111111111" : fdr.getOriginatorBankVerificationNumber(),
											fdr.getOriginatorKYCLevel() == 0 ? 1 : fdr.getBeneficiaryKYCLevel(),
											fdr.getTransactionLocation().isEmpty() ? "12345" : fdr.getTransactionLocation(),
											fdr.getNarration().isEmpty() ? "NIP INWARD TRANSFER" : fdr.getNarration(),
											fdr.getPaymentReference().isEmpty() ? fdr.getSessionId() :fdr.getPaymentReference(),
											fdr.getAmount(),
											fdr.getResponseCode(),
											serviceType);


		return result >= 0;
		
	}	
}
