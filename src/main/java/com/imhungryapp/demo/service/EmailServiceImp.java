package com.imhungryapp.demo.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.imhungryapp.demo.dto.Email;
import com.imhungryapp.demo.dto.ResponseService;
import com.imhungryapp.demo.util.Constants;

@Service
public class EmailServiceImp  implements EmailService{
	
	final static Logger logger = Logger.getLogger(SmsServiceImpl.class);

    @Autowired
    private JavaMailSender emailSender;
    
    ResponseService response = new ResponseService();
    Gson gson = new Gson();

    public void sendSimpleMessage(final Email mail){
    	try {
    		SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(mail.getSubject());
            message.setText(mail.getContent());
            message.setTo(mail.getTo());
            message.setFrom(mail.getFrom());
            emailSender.send(message);
            response.setStatus(Constants.SUCCESS);
			response.setMessage("Email sent!");
			logger.info("This is error : " + (gson.toJson(response)));
    	}catch(MailAuthenticationException ex) {
    		response.setStatus(Constants.FAIL);
			response.setMessage(ex.getMessage());
			logger.error("This is error : " + (gson.toJson(response)));
    	}catch(MailSendException ex) {
    		response.setStatus(Constants.FAIL);
			response.setMessage(ex.getMessage());
			logger.error("This is error : " + (gson.toJson(response)));
    	}
    }

}