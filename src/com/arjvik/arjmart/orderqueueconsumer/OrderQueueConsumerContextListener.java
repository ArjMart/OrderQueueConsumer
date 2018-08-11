package com.arjvik.arjmart.orderqueueconsumer;

import java.util.Arrays;
import java.util.List;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.jooq.DSLContext;

import com.arjvik.arjmart.orderqueueconsumer.handlers.IncomingShipmentHandler;
import com.arjvik.arjmart.orderqueueconsumer.handlers.OrderLineFulfillmentHandler;
import com.arjvik.arjmart.orderqueueconsumer.handlers.OrderPlacedHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderQueueConsumerContextListener implements ServletContextListener {

	private static final List<Class<? extends MessageListener>> handlers = Arrays.asList(
			(Class<? extends MessageListener>) IncomingShipmentHandler.class,
			(Class<? extends MessageListener>) OrderLineFulfillmentHandler.class,
			(Class<? extends MessageListener>) OrderPlacedHandler.class
	);
	
	private Connection connection;
	private Injector injector;
	
	@Override
	@SneakyThrows(JMSException.class)
	public void contextInitialized(ServletContextEvent sce) {
		log.info("Begin initializing");
		injector = Guice.createInjector(new ApplicationModule());
		connection = injector.getInstance(Connection.class);
		Session session = injector.getInstance(Session.class);
		for (Class<? extends MessageListener> clazz : handlers) {
			MessageQueueHandler annotation = clazz.getAnnotation(MessageQueueHandler.class);
			MessageListener listener = injector.getInstance(clazz);
			Destination destination = session.createQueue(annotation.value());
			MessageConsumer consumer = session.createConsumer(destination);
			log.info("Setting Message Listener for Queue \""+annotation.value()+"\" to new instance of class \""+clazz.getName()+"\"");
			consumer.setMessageListener(listener);
		}
		log.info("Finished Initializing");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			connection.close();
			connection = null;
			DSLContext database = injector.getInstance(DSLContext.class);
			database.close();
		} catch (JMSException e) {
			e.printStackTrace();
			throw new RuntimeException("JMSException: "+e.getMessage(), e);
		}
	}

}
