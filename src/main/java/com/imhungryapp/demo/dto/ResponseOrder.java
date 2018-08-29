package com.imhungryapp.demo.dto;

import com.imhungryapp.demo.model.DeliveryTime;

public class ResponseOrder {

	private DeliveryTime deliveryTime;
	private String emailStatus;
	private String smsStatus;
	
	public ResponseOrder(){}
	
	public ResponseOrder(DeliveryTime dt){
		deliveryTime = dt;
	}
	
	public DeliveryTime getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(DeliveryTime deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getEmailStatus() {
		return emailStatus;
	}
	public void setEmailStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	}
	public String getSmsStatus() {
		return smsStatus;
	}
	public void setSmsStatus(String smsStatus) {
		this.smsStatus = smsStatus;
	}
	
	
}
