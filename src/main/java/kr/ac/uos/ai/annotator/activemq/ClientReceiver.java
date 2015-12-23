package kr.ac.uos.ai.annotator.activemq;

import kr.ac.uos.ai.annotator.analyst.RequestAnalyst;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ClientReceiver implements Runnable {

	private String queueName;
	private ActiveMQConnectionFactory factory;
	private Connection connection;
	private Session session;
	private Queue queue;
	private MessageConsumer consumer;
	private TextMessage message;
	private RequestAnalyst requestAnalyst;
    private String serverIP;
    private Sender sender;

    public ClientReceiver() {
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	private void consume() {
		try {
			message = (TextMessage) consumer.receive();
			requestAnalyst.analysis(message);
        } catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			while (true) {
				consume();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init() {
		factory = new ActiveMQConnectionFactory("tcp://" + serverIP + ":61616");
		try {
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			queue = session.createQueue(queueName);
			consumer = session.createConsumer(queue);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void setRequestAnalyst(RequestAnalyst requestAnalyst) {
		this.requestAnalyst = requestAnalyst;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

    public void setSender(Sender sender) {
        this.sender = sender;
    }
}
