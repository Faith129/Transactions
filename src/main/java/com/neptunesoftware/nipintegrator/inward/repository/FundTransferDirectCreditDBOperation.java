package com.neptunesoftware.nipintegrator.inward.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class FundTransferDirectCreditDBOperation{
	private final JdbcTemplate jdbcTemplate;

	public boolean isAccountBlocked(String accountNumber) {
		String sql = "select count(*) from dp_acct where acct_no = ? and status = 'Locked'";

		int result = jdbcTemplate.queryForObject(sql, Integer.class, accountNumber);
		
		return result > 0;
	}
}
