package com.imhungryapp.demo.service;

import org.apache.kafka.clients.producer.Producer;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.imhungryapp.demo.model.CommonMessage;
import com.imhungryapp.demo.model.GenericMessage;

@Service
public class KafkaProducerService {

/*	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	 
	public void sendMessage(String topic, String msg) {
	  *  kafkaTemplate.send(topic, msg);
	}*/
	
	 public static final Logger LOGGER = (Logger) LoggerFactory.getLogger(KafkaProducerService.class);

	    @Autowired
	    private KafkaTemplate<String, CommonMessage> kafkaTemplate;

	    @Autowired
	    @Qualifier("genericKafkaTemplate")
	    private KafkaTemplate<String, GenericMessage> genericKafkaTemplate;

	    public void send(String topic, CommonMessage message) {
	        ListenableFuture<SendResult<String, CommonMessage>> future = kafkaTemplate.send(topic, message);
	        future.addCallback(new ListenableFutureCallback<SendResult<String, CommonMessage>>() {

	            @Override
	            public void onSuccess(final SendResult<String, CommonMessage> stringStringSendResult) {
	                LOGGER.info("sent message= " + message + " with offset= " + stringStringSendResult.getRecordMetadata().offset());
	            }

	            @Override
	            public void onFailure(final Throwable throwable) {
	                LOGGER.error("unable to send message= " + message, throwable);
	            }
	        });
	    }

	    public void sendGeneric(String topic, GenericMessage message) {
	        ListenableFuture<SendResult<String, GenericMessage>> future = genericKafkaTemplate.send(topic, message.getResourceId(), message);
	        future.addCallback(new ListenableFutureCallback<SendResult<String, GenericMessage>>() {

	            @Override
	            public void onSuccess(final SendResult<String, GenericMessage> stringStringSendResult) {
	                LOGGER.info("sent message= " + message + " with offset= " + stringStringSendResult.getRecordMetadata().offset());
	            }

	            @Override
	            public void onFailure(final Throwable throwable) {
	                LOGGER.error("unable to send message= " + message, throwable);
	            }
	        });
	    }
}
