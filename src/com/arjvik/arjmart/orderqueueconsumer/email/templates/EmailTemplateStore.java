package com.arjvik.arjmart.orderqueueconsumer.email.templates;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.inject.Inject;

import lombok.SneakyThrows;

public class EmailTemplateStore {
	
	public Properties templates;
	
	@Inject
	@SneakyThrows(IOException.class)
	public EmailTemplateStore() {
		templates = new Properties();
		InputStream in = getClass().getClassLoader().getResourceAsStream("email-templates.properties");
		if (in == null)
			throw new RuntimeException("Could not read Email Templates, check if email-templates.properties exists in classpath");
		templates.load(in);
	}
	
	public String getTemplate(String name) {
		return templates.getProperty(name);
	}
}
