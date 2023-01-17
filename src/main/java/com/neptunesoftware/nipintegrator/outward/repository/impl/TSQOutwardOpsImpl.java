package com.neptunesoftware.nipintegrator.outward.repository.impl;

import com.neptunesoftware.nipintegrator.NIP.constants.ResponseConstants;
import com.neptunesoftware.nipintegrator.outward.api.TransactionProperties;
import com.neptunesoftware.nipintegrator.outward.api.tsq.TransactionStatusQueryResponse;
import com.neptunesoftware.nipintegrator.outward.repository.TSQOutwardOps;
import com.neptunesoftware.nipintegrator.outward.api.responsemodel.ResponseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Map;

import static com.neptunesoftware.nipintegrator.inward.repository.SqlQueries.SAVE_TRANSACTION_STATUS_QUERY;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TSQOutwardOpsImpl implements TSQOutwardOps {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public ResponseModel postTransactions(TransactionProperties properties) {

        Map<String, Object> result = executeStoredProcedure(properties);

       // Optional<String> errorMessage = Optional.ofNullable(result.get("psErrorMessage").toString());
        String errorCode = result.get("pnErrorCode").toString();
        System.out.println(result);
        System.out.println("Error code = " + errorCode);
        ResponseModel responseModel = new ResponseModel();
        if (errorCode.trim().equals("0")) {
            responseModel.setResponseCode(ResponseConstants.SUCCEESS_CODE);
            responseModel.setResponseMessage(ResponseConstants.SUCCEESS_MESSAGE);
        }
        return responseModel;
    }

    @Override
    public boolean saveTSQ(TransactionStatusQueryResponse response) {
         jdbcTemplate.update(SAVE_TRANSACTION_STATUS_QUERY, response.getSessionId(), response.getSourceInstitutionCode(),
                 response.getChannelCode(), response.getResponseCode());
         return false;
    }

    private Map<String, Object> executeStoredProcedure(TransactionProperties properties) {

        int initiatingApp = 96;
        int chargeCode = 660;
        String reversalFlag = properties.isReversal() ? "Y" : "N";
        BigDecimal chargeAmount = BigDecimal.ZERO;
        BigDecimal taxAmount = BigDecimal.ZERO;

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("csp_nip_TranPosting");

        SqlParameterSource inputParams = new MapSqlParameterSource()
                .addValue("psInitiatingApp", initiatingApp, Types.INTEGER)
                .addValue("psFromAccountNumber", properties.getFromAccountNumber(), Types.VARCHAR)
                .addValue("psToAccountNumber", properties.getToAccountNumber(), Types.VARCHAR)
                .addValue("pnTransactionAmount", properties.getTransactionAmount(), Types.DECIMAL)
                .addValue("psTransactionDescription", properties.getTransactionDescription(), Types.VARCHAR)
                .addValue("psTransactionReference", properties.getTransactionReference(), Types.VARCHAR)
                .addValue("pnChargeCode", chargeCode, Types.SMALLINT)
                .addValue("pnChargeAmount", chargeAmount, Types.DECIMAL)
                .addValue("pnTaxAmount", taxAmount, Types.DECIMAL)
                .addValue("psDebitCredit", properties.getDebitCreditFlag(), Types.VARCHAR)
                .addValue("psReversalFlg", reversalFlag, Types.CHAR)
                .addValue("pnErrorCode", 0, Types.INTEGER)
                .addValue("psErrorMessage", "hhh", Types.VARCHAR);
        return simpleJdbcCall.execute(inputParams);
    }
}
