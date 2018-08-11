package com.arjvik.arjmart.orderqueueconsumer.database;

import org.jooq.DSLContext;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class DatabaseModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(DSLContext.class).toProvider(DatabaseConnectionFactory.class).in(Scopes.SINGLETON);
	}
	
}
