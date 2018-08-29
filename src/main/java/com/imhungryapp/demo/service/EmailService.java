package com.imhungryapp.demo.service;

import javax.mail.AuthenticationFailedException;

import com.imhungryapp.demo.model.Email;


public interface EmailService {

	abstract void sendSimpleMessage(final Email mail)  throws AuthenticationFailedException;
}
