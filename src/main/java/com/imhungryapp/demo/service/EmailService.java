package com.imhungryapp.demo.service;

import com.imhungryapp.demo.model.Email;


public interface EmailService {

	abstract void sendSimpleMessage(final Email mail);
}
