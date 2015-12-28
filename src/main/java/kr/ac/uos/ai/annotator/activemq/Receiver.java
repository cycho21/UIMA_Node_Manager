package kr.ac.uos.ai.annotator.activemq;

import kr.ac.uos.ai.annotator.analyst.RequestAnalyst;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Receiver implements Runnable {

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

    public Receiver() {
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	private void consume() {
		try {
			message = (TextMessage) consumer.receive();
			System.out.println(message);
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
		factory = new ActiveMQConnectionFactory("tcp://211.109.9.71:61616");
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
