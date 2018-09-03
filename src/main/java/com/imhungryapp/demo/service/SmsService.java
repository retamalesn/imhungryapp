package com.imhungryapp.demo.service;

public interface SmsService {

	abstract void sendSms(String to, String sender, String msg);
}
