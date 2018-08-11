package com.arjvik.arjmart.orderqueueconsumer.email;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import com.google.inject.Provider;

import lombok.SneakyThrows;

public class JavaMailSessionProvider implements Provider<Session> {

	private Session session;
	
	@Override
	public Session get() {
		if(session == null)
			session = getSession();
		return session;
	}

	@SneakyThrows(IOException.class)
	private Session getSession() {
		Properties config = new Properties();
		InputStream in = getClass().getClassLoader().getResourceAsStream("javamail.properties");
		if (in == null)
			throw new RuntimeException("Could not read SMTP Server connection info, check if javamail.properties exists in classpath");
		config.load(in);
		String username = config.getProperty("mail.smtp.username");
		String password = config.getProperty("mail.smtp.password");
		Session session = Session.getInstance(config, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		session.setDebug(true);
		return session;
	}

}
