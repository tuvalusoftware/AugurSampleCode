package com.augur.sample.pojos;

import java.math.BigDecimal;
/*
 * 
 *  record data    
 * 
 */
public class RecordFoundEventPOJO {

	private String sender;
	private String recipient;
	private BigDecimal value;
	public Long timestamp;

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isFirstEntry() {
		boolean isFirst = false;
		if (!isValid(sender)) {
			isFirst = true;
		}
		return isFirst;
	}

	public boolean isValid() {
		boolean isValid = true;

		if (!isValid(recipient) || value.compareTo(BigDecimal.ZERO) == 0) {
			isValid = false;
		}

		return isValid;
	}

	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("************************************");
		sb.append("\ntoken: ").append(token);
		sb.append("\nsender: ").append(sender);
		sb.append("\nrecipient: ").append(recipient);
		sb.append("\nvalue: ").append(value.toString());
		sb.append("\ntimestamp: ").append(timestamp);
		sb.append("\n************************************");
		return sb.toString();
	}

	public boolean isValid(String str) {

		boolean isValid = true;

		// check if string is null
		if (str == null || str.isEmpty()) {
			isValid = false;
		}

		return isValid;

	}

}
