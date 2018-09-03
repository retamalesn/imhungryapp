package com.imhungryapp.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.imhungryapp.demo.dto.Email;
import com.imhungryapp.demo.dto.MapsDto;
import com.imhungryapp.demo.dto.SmsDto;

import springfox.documentation.schema.Maps;

@Service
public class KafkaConsumerService {
	
	@Autowired
	private SmsService smsService;
	@Autowired
	private EmailService emailService;
	@Autowired
	DistanceMatrixService distanceMatrixService;
	
	Gson gson = new Gson();
	
	@KafkaListener(topics = "email")
	public void listenEmail(String message) {
		Email email = gson.fromJson(message, Email.class);
		emailService.sendSimpleMessage(email);
	}
	
	@KafkaListener(topics = "sms")
	public void listenSms(String message) {
	    SmsDto smsDto = gson.fromJson(message, SmsDto.class);
	    smsService.sendSms(smsDto.getNumber(), smsDto.getSender(), smsDto.getMsg());
	}
	
	@KafkaListener(topics = "maps")
	public void listenMaps(String message) {
		MapsDto mapsDTO = gson.fromJson(message, MapsDto.class);
		distanceMatrixService.getDeliveryTime(mapsDTO.getOrigins(), mapsDTO.getDestinations());
	}

}
