package com.imhungryapp.demo.dto;

import java.io.Serializable;

public class DeliveryTime implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String time;
	private String status;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
