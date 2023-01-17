package com.neptunesoftware.nipintegrator.NIP;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "NIPInfo")
@XmlAccessorType(XmlAccessType.FIELD)

public class NIPInfoData {

	@XmlElement(name = "Username")
	private String username;
	
	@XmlElement(name = "Password")
	private String password;
	
	@XmlElement(name = "BankCode")
	private String bankCode;

	@XmlElement(name = "PublicKeyName")
	private String publicKeyName;
	
	@XmlElement(name = "TransferLimit")
	private String transferLimit;

	@XmlElement(name = "WsdlUrl")
	private String wsdlURL;
	
	@XmlElement(name = "TSQWsdl")
	private String tsqWsdl;
	
	@XmlTransient
	private String responseCode;

	public String getUsername() {
		return username == null ? "" : username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password == null ? "" : password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBankCode() {
		return bankCode == null ? "" : bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getPublicKeyName() {
		return publicKeyName == null ? "" : publicKeyName;
	}

	public void setPublicKeyName(String publicKeyName) {
		this.publicKeyName = publicKeyName;
	}
	
	public String getTransferLimit() {
		return transferLimit == null ? "" : transferLimit;
	}

	public void setTransferLimit(String transferLimit) {
		this.transferLimit = transferLimit;
	}
	
	public String getWsdlURL() {
		return wsdlURL;
	}

	public void setWsdlURL(String wsdlURL) {
		this.wsdlURL = wsdlURL;
	}

	public String getTsqWsdl() {
		return tsqWsdl;
	}

	public void setTsqWsdl(String tsqWsdl) {
		this.tsqWsdl = tsqWsdl;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	
	
	
}
