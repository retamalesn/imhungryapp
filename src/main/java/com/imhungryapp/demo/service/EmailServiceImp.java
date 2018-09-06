package com.imhungryapp.demo.service;

import javax.mail.AuthenticationFailedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.imhungryapp.demo.dto.Email;

@Service
public class EmailServiceImp  implements EmailService{

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(final Email mail, final String to) throws AuthenticationFailedException{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(mail.getSubject());
        message.setText(mail.getContent());
        message.setTo(to);
        message.setFrom(mail.getFrom());

        emailSender.send(message);
    }

}