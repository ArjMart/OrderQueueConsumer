package com.arjvik.arjmart.orderqueueconsumer.jms;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.google.inject.Provider;

import lombok.SneakyThrows;

public class ActiveMQConnectionProvider implements Provider<Connection> {
	
	private Connection connection;

	@Override
	public Connection get() {
		if(connection == null)
			connection = getConnection();
		else System.err.println("ERROR | ACTIVEMQ CONNECTION ISN'T SINGLETON");
		return connection;
	}

	@SneakyThrows({JMSException.class, IOException.class})
	private Connection getConnection() {
		Properties properties = new Properties();
		InputStream in = ActiveMQSessionProvider.class.getClassLoader().getResourceAsStream("jms.properties");
		if (in == null)
			throw new RuntimeException("Could not read Order Queue connection info, check if jms.properties exists in classpath");
		properties.load(in);
		String CONNECTION_URL = properties.getProperty("jmsurl");
		String USERNAME = properties.getProperty("jmsusername");
		String PASSWORD = properties.getProperty("jmspassword");
		if (CONNECTION_URL == null)
			throw new RuntimeException("Could not read Order Queue connection info, check format of jms.properties ");
		ConnectionFactory connectionFactory;
		if(USERNAME == null || USERNAME.isEmpty())
			connectionFactory = new ActiveMQConnectionFactory(CONNECTION_URL);
		else
			connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, CONNECTION_URL);
		Connection c = connectionFactory.createConnection();
		c.start();
		return c;
	}
	
}
