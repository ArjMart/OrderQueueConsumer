package com.arjvik.arjmart.orderqueueconsumer.jms;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;


public class JMSServiceImpl implements JMSService {

	private Session session;
	private ObjectMapper mapper;
	private Map<String, MessageProducer> producers = new HashMap<>();
	
	@Inject
	public JMSServiceImpl(Session session, ObjectMapper mapper) {
		this.session = session;
		this.mapper = mapper;
	}
	
	@Override
	public void sendMessage(String queueName, String messageText) throws JMSException {
		MessageProducer producer = getDestination(queueName);
		TextMessage message = session.createTextMessage(messageText);
		producer.send(message);
	}

	private MessageProducer getDestination(String queueName) throws JMSException {
		if(producers.containsKey(queueName))
			return producers.get(queueName);
		Destination destination = session.createQueue(queueName);
		MessageProducer producer = session.createProducer(destination);
		producers.put(queueName, producer);
		return producer;
	}

	@Override
	public void sendJsonMessage(String queueName, Object message) throws JsonProcessingException, JMSException {
		String json = mapper.writeValueAsString(message);
		sendMessage(queueName, json);
	}

}
