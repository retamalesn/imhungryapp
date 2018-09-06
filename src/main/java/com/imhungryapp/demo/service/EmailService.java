package com.imhungryapp.demo.service;

import javax.mail.AuthenticationFailedException;

import com.imhungryapp.demo.dto.Email;


public interface EmailService {

	abstract void sendSimpleMessage(final Email mail, final String to)  throws AuthenticationFailedException;
}
