//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.06.24 at 01:39:54 PM WAT 
//


package com.neptunesoftware.nipintegrator.inward.model.accountunblock;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sessionId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="destinationInstitutionCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="channelCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="referenceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="targetAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="targetAccountName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="reasonCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="narration" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="targetBankVerificationNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "sessionId",
    "destinationInstitutionCode",
    "channelCode",
    "referenceCode",
    "targetAccountNumber",
    "targetAccountName",
    "reasonCode",
    "narration",
    "targetBankVerificationNumber"
})
@XmlRootElement(name = "AccountUnblockRequest")
public class AccountUnblockRequest {

    @XmlElement(required = true)
    protected String sessionId;
    @XmlElement(required = true)
    protected String destinationInstitutionCode;
    protected int channelCode;
    @XmlElement(required = true)
    protected String referenceCode;
    @XmlElement(required = true)
    protected String targetAccountNumber;
    @XmlElement(required = true)
    protected String targetAccountName;
    @XmlElement(required = true)
    protected String reasonCode;
    @XmlElement(required = true)
    protected String narration;
    @XmlElement(required = true)
    protected String targetBankVerificationNumber;

    /**
     * Gets the value of the sessionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Sets the value of the sessionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionId(String value) {
        this.sessionId = value;
    }

    /**
     * Gets the value of the destinationInstitutionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinationInstitutionCode() {
        return destinationInstitutionCode;
    }

    /**
     * Sets the value of the destinationInstitutionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinationInstitutionCode(String value) {
        this.destinationInstitutionCode = value;
    }

    /**
     * Gets the value of the channelCode property.
     * 
     */
    public int getChannelCode() {
        return channelCode;
    }

    /**
     * Sets the value of the channelCode property.
     * 
     */
    public void setChannelCode(int value) {
        this.channelCode = value;
    }

    /**
     * Gets the value of the referenceCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceCode() {
        return referenceCode;
    }

    /**
     * Sets the value of the referenceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceCode(String value) {
        this.referenceCode = value;
    }

    /**
     * Gets the value of the targetAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetAccountNumber() {
        return targetAccountNumber;
    }

    /**
     * Sets the value of the targetAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetAccountNumber(String value) {
        this.targetAccountNumber = value;
    }

    /**
     * Gets the value of the targetAccountName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetAccountName() {
        return targetAccountName;
    }

    /**
     * Sets the value of the targetAccountName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetAccountName(String value) {
        this.targetAccountName = value;
    }

    /**
     * Gets the value of the reasonCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReasonCode() {
        return reasonCode;
    }

    /**
     * Sets the value of the reasonCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReasonCode(String value) {
        this.reasonCode = value;
    }

    /**
     * Gets the value of the narration property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNarration() {
        return narration;
    }

    /**
     * Sets the value of the narration property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNarration(String value) {
        this.narration = value;
    }

    /**
     * Gets the value of the targetBankVerificationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetBankVerificationNumber() {
        return targetBankVerificationNumber;
    }

    /**
     * Sets the value of the targetBankVerificationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetBankVerificationNumber(String value) {
        this.targetBankVerificationNumber = value;
    }

}
