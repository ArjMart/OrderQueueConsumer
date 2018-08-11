package com.arjvik.arjmart.orderqueueconsumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class JacksonModule extends AbstractModule {

	@Override
	public void configure() {
		bind(ObjectMapper.class).in(Scopes.SINGLETON);
	}
}
