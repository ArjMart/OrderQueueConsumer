package com.arjvik.arjmart.orderqueueconsumer.jms;

import javax.jms.Connection;
import javax.jms.Session;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class JMSModule extends AbstractModule {

	@Override
	public void configure() {
		bind(Connection.class).toProvider(ActiveMQConnectionProvider.class).in(Scopes.SINGLETON);
		bind(Session.class).toProvider(ActiveMQSessionProvider.class).in(Scopes.SINGLETON);
		bind(JMSService.class).to(JMSServiceImpl.class).in(Scopes.SINGLETON);
	}

}
