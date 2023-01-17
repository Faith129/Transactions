package com.neptunesoftware.nipintegrator.NIP;

import java.io.StringWriter;
import java.net.URL;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

@Service
public class SoapWebserviceCall {
		
	public static String soapWebService(String wsdlUrl, SOAPMessage soapRequest) throws Exception {
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		//
		URL endpoint = new URL(wsdlUrl.trim());
		SOAPMessage soapResponse = soapConnection.call(soapRequest, endpoint);
		String strResult = createSoapResponse(soapResponse);
		soapConnection.close();

		return strResult;
	}

	private static String createSoapResponse(SOAPMessage soapResponse) throws Exception {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		
		Document doc = soapResponse.getSOAPBody().extractContentAsDocument();
		
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		//transformer.transform(sourceContent, result);
		
		transformer.transform(new DOMSource(doc), result);

		return writer.toString();
	}

	public static Object createSoapResponseObject(String wsdlUrl, SOAPMessage soapRequest) throws Exception {
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		// hit soapRequest to the server to get response 
		SOAPMessage soapResponse = soapConnection.call(soapRequest, wsdlUrl);
		Object Content = soapResponse.getSOAPBody().extractContentAsDocument();
		soapConnection.close();
		return Content;
	}
	
	
}





