package com.arjvik.arjmart.orderqueueconsumer.email;

public interface EmailService {

	public void sendEmail(String to, String from, String fromName, String subject, String body) throws EmailException;

}
