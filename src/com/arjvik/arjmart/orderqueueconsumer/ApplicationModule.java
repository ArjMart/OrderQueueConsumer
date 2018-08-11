package com.arjvik.arjmart.orderqueueconsumer;

import com.arjvik.arjmart.orderqueueconsumer.database.DatabaseModule;
import com.arjvik.arjmart.orderqueueconsumer.email.EmailModule;
import com.arjvik.arjmart.orderqueueconsumer.jms.JMSModule;
import com.google.inject.AbstractModule;

public class ApplicationModule extends AbstractModule {
	
	@Override
	protected void configure() {
		install(new JMSModule());
		install(new JacksonModule());
		install(new DatabaseModule());
		install(new EmailModule());
	}

}
