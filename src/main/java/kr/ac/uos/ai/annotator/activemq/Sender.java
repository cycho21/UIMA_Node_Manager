package kr.ac.uos.ai.annotator.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Sender {
	private ActiveMQConnectionFactory factory;
	private Connection connection;
	private Session session;
	private Queue queue;
	private MessageProducer producer;

	public Sender() {
	
	}

	public void createQueue(String queueName) {
		try {
			queue = session.createQueue(queueName);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		set();
	}

	private void set() {
		try {
			producer = session.createProducer(queue);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void init() {
		factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		try {
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(byte[] msg) {
		BytesMessage message;
		try {
			message = session.createBytesMessage();
			message.writeBytes(msg);
			producer.send(message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}