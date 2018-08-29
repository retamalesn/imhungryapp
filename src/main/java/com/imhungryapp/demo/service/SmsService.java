package com.imhungryapp.demo.service;

public interface SmsService {

	abstract String sendSms(String to, String sender, String msg);
}
