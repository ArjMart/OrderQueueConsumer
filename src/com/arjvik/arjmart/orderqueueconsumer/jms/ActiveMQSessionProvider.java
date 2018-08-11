package com.arjvik.arjmart.orderqueueconsumer.jms;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

import com.google.inject.Inject;
import com.google.inject.Provider;

import lombok.SneakyThrows;

public class ActiveMQSessionProvider implements Provider<Session> {

	private Connection connection;
	private Session session;
	
	@Inject
	public ActiveMQSessionProvider(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public Session get() {
		if(session == null)
			session = getSession();
		else System.err.println("ERROR | ACTIVEMQ SESSION ISN'T SINGLETON");
		return session;
	}

	@SneakyThrows(JMSException.class)
	private Session getSession() {
		return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

}
