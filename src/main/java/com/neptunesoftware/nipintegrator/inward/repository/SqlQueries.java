package com.neptunesoftware.nipintegrator.inward.repository;

public final class SqlQueries {

    public static final String UPDATE_DP_DISPLAY_AMOUNT_BLOCK = "update	dp_display set  	hold_bal = hold_bal +  ? where  	acct_no = ltrim(rtrim(?)) and  	acct_type =  ltrim(rtrim(?))";

    // Prevents direct instantiation of this class
    private SqlQueries() {
    }

    // NAME ENQUIRY: 
    public static final String NAME_ENQUIRY = "SELECT b.acct_no, a.cbn_bvn_no bvn, b.title_1 name FROM rm_acct a, dp_acct b WHERE a.rim_no = b.rim_no AND b.acct_no = ?";

    // BALANCE ENQUIRY: 
    public static final String BALANCE_ENQUIRY = "SELECT acct_type, cur_bal, col_bal, hold_bal, memo_dr, memo_cr,od_limit, Float_bal_1, Float_bal_2 memo_float from dp_display WHERE acct_no = ? AND acct_type = ?";
    public static final String SELECT_ACCOUNT_TYPE = "SELECT acct_type FROM dp_acct WHERE acct_no = ?";

    // ACCOUNT UNBLOCK: 
    public static final String SELECT_ALL_USED_REFERENCE_CODE = "SELECT * FROM nip_account_block WHERE reference_code=? AND lock_status= 'N'";
    public static final String SELECT_ALL_WITH_REFERENCE_CODE_AND_ACCT_NUM = "SELECT * FROM nip_account_block WHERE reference_code=? AND target_account_number=?";
    public static final String UPDATE_NIP_ACCOUNT_BLOCK_TABLE = "UPDATE nip_account_block SET lock_status='N' WHERE reference_code=? AND target_account_number=?";
    public static final String UPDATE_DP_ACCOUNT_UNBLOCK_ACCOUNT =  "UPDATE dp_acct SET status = 'Active', row_version = row_version + 1 where acct_no = ltrim(rtrim(?)) AND acct_type =  ltrim(rtrim(?))";
    public static final String UPDATE_DP_DISPLAY_UNBLOCK_ACCOUNT =  "update dp_display set status = 'Active', row_version = row_version + 1 where acct_no = ltrim(rtrim(?)) and acct_type =  ltrim(rtrim(?))";
    public static final String ACCOUNT_STATUS = "SELECT status FROM dp_acct where acct_no =  ltrim(rtrim(?))";

    public static final 	String ACCOUNT_STATUS_LOCKED = "select a.status from dp_acct a where a.acct_no = ? and a.status = 'Locked'";

 // AMOUNT UNBLOCK: 
    public static final String IS_REFERENCE_CODE_VALID = "SELECT * from nip_amount_block where reference_code=? and block_status='Y'";
    public static final String GET_BLOCKED_AMOUNT  = "select * from nip_amount_block where reference_code=? and block_status='Y' and target_account_number=?";
    public static final String IS_REFERENCE_CODE_USED_BEFORE_FOR_UNBLOCK = "select * from nip_amount_block where reference_code=? and block_status='N'";
    public static final String PARTIAL_AMOUNT_UNBLOCK   = "update nip_amount_block set balance_amount=? where reference_code=? and target_account_number=? and block_status='Y'";
    public static final String FULL_AMOUNT_UNBLOCK= "update nip_amount_block set block_status='N', balance_amount=0 where reference_code=? and target_account_number=?";
    public static final String UPDATE_DP_DISPLAY_AMOUNT_UNBLOCK = "UPDATE dp_display SET hold_bal = hold_bal - ? WHERE acct_no = ltrim(rtrim(?)) AND acct_type =  ltrim(rtrim(?))";
    public static final String UPDATE_GB_HOLD_STOP_AMOUNT_UNBLOCK =  "UPDATE	gb_hold_stop SET status = 'Closed', status_sort = 240,  row_version = row_version + 1 WHERE	acct_no =  ltrim(rtrim(?)) AND acct_type =  ltrim(rtrim(?)) AND instructions = ?";


 // ACCOUNT BLOCK
    // save request data to nip account block table
    public static final String INSERT_ACCT_BLOCK = "insert into nip_account_block (session_id, " +
            "destination_institution_code, channel_code, reference_code, target_account_name, " +
            "target_account_number, target_bvn, reason_code, narration, lock_status, response_code)"
            + " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String ACCT_TYPE = "select acct_type from  dp_acct where acct_no = ?";
   // public static final String REFERENCE_CODE = "select count(*) from nip_account_block where reference_code=?";

    public static final String DP_DISPLAY = "update dp_display " +
            "set  status = 'Locked', " +
            "row_version = row_version + 1 " +
            "where acct_no = ltrim(rtrim(?)) " +
            "and acct_type =  ltrim(rtrim(?))";

    public static final String DP_ACCT = "update dp_acct " +
            "set  status = 'Locked', " +
            "row_version = row_version + 1 " +
            "where acct_no = ltrim(rtrim(?)) " +
            "and acct_type =  ltrim(rtrim(?))";

    public static final String ACCOUNT_BLOCK_REFERENCE_CODE = "SELECT * from nip_account_block where reference_code=?";
 // MANDATE ADVICE 

 public static final String SELECT_MANDATE_ADVICE = "SELECT * from nip_mandate_advice WHERE mandate_reference_number = ? AND response_code = ?";
 public static final String UPDATE_MANDATE_ADVICE_TABLE = "INSERT INTO nip_mandate_advice(session_id, destination_institution_code, channel_code, mandate_reference_number, amount, deb_account_name, deb_account_number, deb_bvn, deb_kyclevel, ben_account_name, ben_account_number, ben_bvn, ben_kyclevel, response_code) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

// AMOUNT bLOCK

 public static final String AMOUNT_BLOCK = "INSERT into nip_amount_block (session_id, destination_institution_code, " +
            "channel_code, reference_code, target_account_name, target_account_number, target_bvn, reason_code, " +
            "narration, block_status, amount, balance_amount, response_code) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String REFERENCE_CODE = "SELECT * from nip_amount_block where reference_code=?";
    public static final String UPDATE_DP_ACCOUNT = "UPDATE dp_acct set  status = 'Locked',  row_version = 1 where acct_no = ltrim(rtrim(?)) and acct_type = ltrim(rtrim(?))";
    public static final String UPDATE_DP_DISPLAY = "UPDATE dp_display  set      status = 'Active', " +
            "row_version = row_version + 1 where  acct_no = ltrim(rtrim(?)) and acct_type =  ltrim(rtrim(?))";

    public static final String INSERT_INTO_GB_HOLD_STOP = "	insert into gb_hold_stop\r\n" +
            "		(\r\n" +
            "		acct_no, 			acct_type, 			hold_id, 			effective_dt,  \r\n" +
            "		status_sort, 			status, 			empl_id, 			create_dt, \r\n" +
            "		row_version, 			ptid, 				memo_type, 			hold_stop_type, \r\n" +
            "		amt, 				chk_dt, 			expiry_dt, 			low_chk_no, \r\n" +
            "		high_chk_no, 			coll_acct_no, 			coll_acct_type, 		fee_charged, \r\n" +
            "		description, 			origin, 			atm_tracer_no, 			hit_option, \r\n" +
            "		instructions, 			history_ptid, 			ln_tfr_hold_yn, 		ln_sal_hold_yn, \r\n" +
            "		ln_hold_id, 			ln_acct_no, 			ln_acct_type  \r\n" +
            "		) \r\n" +
            "	values \r\n" +
            "		( \r\n" +
            "		?,			?,			?,			?,\r\n" +
            "		10, 				'Active', 			0, 				getdate(), \r\n" +
            "		1, 				?, 			'', 				'Hold', \r\n" +
            "		?, 			null, 				?, 			null, \r\n" +
            "		null, 				null, 				null, 				'N', \r\n" +
            "		?,			null, 				null, 				'B', \r\n" +
            "		?,			null, 				null, 				null, \r\n" +
            "		null, 				null, 				null \r\n" +
            "		)";
    public static final String SELECT_EFFECTIVE_DATE = "SELECT dateadd(day,1,last_to_dt) effective_date from ov_control";
    public static final String SELECT_EXPIRY_DATE = "SELECT dateadd(day,365,last_to_dt) expiry_date from ov_control";
    public static final String executeCsp_get_dp_posting_def = "Select  AVAIL_BAL, COLL_BAL, POST_BAL From AD_DP_CONTROL";

//FINANACIAL LIST
    public static final String UPDATE_NIP_FINANCIAL_INSTITUTION = "insert into nip_financial_institution(code, name, category) values(?, ?, ?)";

  // FUND_TRANSFER DIRECT DEBIT

    public static final String GET_MANDATE_AMOUNT = "select amount from nip_mandate_advice where mandate_reference_number =  ltrim(rtrim(?)) and response_code =  ltrim(rtrim(?))";
    public static final String SAVE_TRANSACTION_STATUS_QUERY = "INSERT INTO nip_tsq(session_id, source_institution_code, channel_code, response_code) values(?, ?, ?, ?)";
    public static final String SAVE_NIP_TSQ = "INSERT INTO nip_tsq(session_id, source_institution_code, channel_code, response_code) values(?, ?, ?, ?)";

    public static final String SAVE_INTO_NIP_XTER_DIRECT_CREDIT = "insert into nip_xfer_direct_debit(session_id, name_enquiry_ref, destination_institution_code, channel_code, ben_account_name,\r\n" +
            " ben_account_number, ben_bvn, ben_kyclevel, deb_account_name, deb_account_number, deb_bvn, deb_kyclevel, transaction_location,\r\n" +
            " narration, payment_reference, mandate_reference_number, transaction_fee, amount, response_code)\r\n" +
            " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


// FUND_TRANSFER  ADVICE DIRECT CREDIT:

    public static final String PERFORM_FUND_TRANSFER_CREDIT_ADVICE = "select * from nip_xfer_direct_credit where session_id=?";
    public static final String IS_SESSION_ID_VALID = "select * from nip_xfer_direct_credit where session_id=?";
    public static final String IS_TXN_REVERSED_ALREADY = "select * from nip_xfer_direct_credit where session_id=? and is_reversed='Y'";
    public static final String UPDATE_FUND_TRANSFER_REVERSAL_STATUS = "update nip_xfer_direct_credit set is_reversed='Y', reversal_date=? where session_id=?";


 public static final String SELECT_NIP_XFER_DIRECT_DEBIT = "select * from nip_xfer_direct_debit where session_id=?";
    public static final String SELECT_NIP_XTER_DIRECT_DEBIT_REVERSED = "SELECT * from nip_xfer_direct_debit where session_id=? and is_reversed='Y'";
    public static final String UPDATE_FUND_XTER_REVERSAL_STATUS = "UPDATE nip_xfer_direct_debit set is_reversed='Y', reversal_date=? where session_id=?";
   public static final String SELECT_MANDATE_AMOUNT = "select amount from nip_mandate_advice where mandate_reference_number = ? and response_code = ?";


    public static final String UPDATE_DP_DISPLAY_SET_HOLD_BAL = "update dp_display set hold_bal = hold_bal +  ? where  acct_no = ltrim(rtrim(?)) and acct_type =  ltrim(rtrim(?))";
    public static final String SELECT_PTID = "Select PTID From	PC_PTID Where table_name =?";

    public static final String UPDATE_PC_PTID = "Update 	PC_PTID \r\n" +
            "	Set	PTID = PTID + 1 \r\n" +
            "	Where	TABLE_NAME = ?";

    // FUND TRANSFER DIRECT CREDIT

    public static final String IS_ACCOUNT_BLOCKED =  "select a.status \r\n" +
            "from dp_acct a \r\n" +
            "where a.acct_no = ? \r\n" +
            "and a.status = 'Locked'";
}
