package com.arjvik.arjmart.orderqueueconsumer.email;

import com.arjvik.arjmart.orderqueueconsumer.email.templates.EmailTemplateStore;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class EmailModule extends AbstractModule {
	
	@Override
	protected void configure() {
		bind(EmailService.class).to(JavaMailEmailService.class).in(Scopes.SINGLETON);
		bind(javax.mail.Session.class).toProvider(JavaMailSessionProvider.class).in(Scopes.SINGLETON);
		bind(EmailTemplateStore.class).in(Scopes.SINGLETON);
	}
	
}
