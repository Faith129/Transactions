package com.neptunesoftware.nipintegrator.inward.repository.mappers;

import com.neptunesoftware.nipintegrator.inward.repository.database_results.DirectDebitAdvice;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DirectDebitAdviceMapper implements RowMapper<DirectDebitAdvice> {
    @Override
    public DirectDebitAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {
         return DirectDebitAdvice.builder()
                 .sessionId(rs.getString("session_id"))
                .nameEnquiryReference(rs.getString("name_enquiry_ref"))
                .destinationReferenceCode(rs.getString("destination_institution_code"))
                .channelCode(rs.getInt("channel_code"))
                .beneficiaryAccountName(rs.getString("ben_account_name"))
                .beneficiaryAccountNumber(rs.getString("ben_account_number"))
                .beneficiaryBankVerificationNumber(rs.getString("ben_bvn"))
                .beneficiaryKYCLevel(rs.getString("ben_kyclevel"))
                .debitAccountName(rs.getString("deb_account_name"))
                .debitAccountNumber(rs.getString("deb_account_number"))
                .debitBVN(rs.getString("deb_bvn"))
                .debitKYCLevel(rs.getString("deb_kyclevel"))
                 .transactionLocation(rs.getString("transaction_location"))
                .narration(rs.getString("narration"))
                .paymentReference(rs.getString("payment_reference"))
                 .mandateReferenceNumber(rs.getString("mandate_reference_number"))
                 .transactionFee(rs.getString("transaction_fee"))
                .amount(rs.getBigDecimal("amount"))
                .responseCode(rs.getString("response_code"))
                .isReversed(rs.getString("is_reversed"))
                .build();
    }
}
