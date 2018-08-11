package com.arjvik.arjmart.orderqueueconsumer.jms;

import javax.jms.JMSException;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface JMSService {
	
	public void sendMessage(String queueName, String messageText) throws JMSException;
	
	public void sendJsonMessage(String queueName, Object message) throws JsonProcessingException, JMSException;
	
}
