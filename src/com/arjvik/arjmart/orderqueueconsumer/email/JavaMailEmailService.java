package com.arjvik.arjmart.orderqueueconsumer.email;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.inject.Inject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JavaMailEmailService implements EmailService {

	private Session session;

	@Inject
	public JavaMailEmailService(Session session) {
		this.session = session;
	}
	
	@Override
	public void sendEmail(String to, String from, String fromName, String subject, String body) throws EmailException {
		log.info("Sending mail - To: <{}> From: \"{}\" <{}> Subject: {}\n{}", to, fromName, from, subject, body);
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from, fromName));
			message.setRecipient(RecipientType.TO, new InternetAddress(to));
			message.setSentDate(new Date());
			message.setSubject(subject);
			message.setContent(body, "text/html; charset=utf-8");
			Transport.send(message);
		} catch (MessagingException | UnsupportedEncodingException e) {
			throw new EmailException(e);
		}
	}

}
