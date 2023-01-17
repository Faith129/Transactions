package com.neptunesoftware.nipintegrator.inward.repository.mappers;

import com.neptunesoftware.nipintegrator.inward.repository.database_results.NIPTransferDirectCreditAdvice;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NIPTransferDirectCreditMapper implements RowMapper<NIPTransferDirectCreditAdvice> {
    @Override
    public NIPTransferDirectCreditAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {
        return NIPTransferDirectCreditAdvice.builder()
                .sessionId(rs.getString("session_id"))
                .nameEnquiryReference(rs.getString("name_enquiry_ref"))
                .destinationReferenceCode(rs.getString("destination_institution_code"))
                .channelCode(rs.getInt("channel_code"))
                .beneficiaryAccountName(rs.getString("ben_account_name"))
                .beneficiaryAccountNumber(rs.getString("ben_account_number"))
                .beneficiaryBankVerificationNumber(rs.getString("ben_bvn"))
                .beneficiaryKYCLevel(rs.getString("ben_kyclevel"))
                .originatorAccountName(rs.getString("ori_account_name"))
                .originatorAccountNumber(rs.getString("ori_account_number"))
                .originatorBVN(rs.getString("ori_bvn"))
                .originatorKYCLevel(rs.getString("ori_kyclevel"))
                .transaction_location(rs.getString("transaction_location"))
                .narration(rs.getString("narration"))
                .paymentReference(rs.getString("payment_reference"))
                .amount(rs.getBigDecimal("amount"))
                .responseCode(rs.getString("response_code"))
                .isReversed(rs.getString("is_reversed"))
                .serviceType(rs.getString("service_type"))
                .build();
    }
}
