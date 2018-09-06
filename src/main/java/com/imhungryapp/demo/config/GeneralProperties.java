package com.imhungryapp.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.imhungryapp.demo.dto.Email;

@Component
@ConfigurationProperties("app")
public class GeneralProperties {
	
	private Email email = new Email();
	private String googleApiKey;
	private String smsApiKey;

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public String getGoogleApiKey() {
		return googleApiKey;
	}

	public void setGoogleApiKey(String googleApiKey) {
		this.googleApiKey = googleApiKey;
	}

	public String getSmsApiKey() {
		return smsApiKey;
	}

	public void setSmsApiKey(String smsApiKey) {
		this.smsApiKey = smsApiKey;
	}
	
	


}
