package com.fh.util.cmcc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MsgType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MsgSeq" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TimeStamp" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PerformCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Skey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Body" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "version", "msgType", "msgSeq", "timeStamp",
		"performCode", "skey", "body" })
@XmlRootElement(name = "Request")
public class Request {

	@XmlElement(name = "Version", required = true, nillable = false)
	protected String version;
	@XmlElement(name = "MsgType", required = true)
	protected String msgType;
	@XmlElement(name = "MsgSeq", required = true)
	protected String msgSeq;
	@XmlElement(name = "TimeStamp", required = true)
	protected String timeStamp;
	@XmlElement(name = "PerformCode", required = true)
	protected String performCode;
	@XmlElement(name = "Skey", required = true)
	protected String skey;
	@XmlElement(name = "Body", required = true)
	protected String body;

	/**
	 * Gets the value of the version property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the value of the version property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setVersion(String value) {
		this.version = value;
	}

	/**
	 * Gets the value of the msgType property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMsgType() {
		return msgType;
	}

	/**
	 * Sets the value of the msgType property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMsgType(String value) {
		this.msgType = value;
	}

	/**
	 * Gets the value of the msgSeq property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMsgSeq() {
		return msgSeq;
	}

	/**
	 * Sets the value of the msgSeq property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMsgSeq(String value) {
		this.msgSeq = value;
	}

	/**
	 * Gets the value of the timeStamp property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTimeStamp() {
		return timeStamp;
	}

	/**
	 * Sets the value of the timeStamp property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTimeStamp(String value) {
		this.timeStamp = value;
	}

	/**
	 * Gets the value of the performCode property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPerformCode() {
		return performCode;
	}

	/**
	 * Sets the value of the performCode property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPerformCode(String performCode) {
		this.performCode = performCode;
	}

	/**
	 * Gets the value of the skey property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSkey() {
		return skey;
	}

	/**
	 * Sets the value of the skey property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSkey(String skey) {
		this.skey = skey;
	}

	/**
	 * Gets the value of the body property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Sets the value of the body property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setBody(String value) {
		this.body = value;
	}

}
