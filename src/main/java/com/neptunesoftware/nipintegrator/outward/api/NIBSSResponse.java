package com.neptunesoftware.nipintegrator.outward.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://core.nip.nibss/")
@XmlAccessorType(XmlAccessType.FIELD)
public class NIBSSResponse {
	
	@XmlElement(name = "return")
	private String response;

	public String getResponse() {
		return response == null ? "" : response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	
}
