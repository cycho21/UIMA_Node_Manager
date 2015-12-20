package kr.ac.uos.ai.annotator.activemq;

import kr.ac.uos.ai.annotator.analyst.RequestAnalyst;
import kr.ac.uos.ai.annotator.taskarchiver.TaskUnpacker;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Receiver implements Runnable {

	private String queueName;
	private ActiveMQConnectionFactory factory;
	private Connection connection;
	private Session session;
	private Queue queue;
	private MessageConsumer consumer;
	private Message message;
	private TextMessage tMsg;
	private TaskUnpacker taskUnpacker;
    private RequestAnalyst requestAnalyst;

    public Receiver() {
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	private void consume() {
		try {
			consumer = session.createConsumer(queue);
			message = consumer.receive();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			while (true) {
				consume();
                requestAnalyst.injectTask(message);
				BytesMessage tMsg = (BytesMessage) message;
				byte[] bytes = new byte[(int) ((BytesMessage) message).getBodyLength()];
//				HashMap task = (HashMap) message.getObjectProperty("objectPropertyTest");
//				System.out.println("Receiver AUTHOR : " + task.get("AUTHOR"));
				tMsg.readBytes(bytes);

				for (byte b : bytes) {
//					System.out.println((char) b);
				}
				makeFile(bytes);
			}
		} catch (Exception e) {
			System.out.println("Receiver Run Error");
		}
	}

	public void init() {
		taskUnpacker = new TaskUnpacker();
        requestAnalyst = new RequestAnalyst();
        requestAnalyst.init();
		factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		try {
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			queue = session.createQueue(queueName);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}


	public void makeFile(byte[] bytes) {
		taskUnpacker.makeFileFromByteArray(System.getProperty("user.dir") + "/lib/TestAnno4.jar", bytes);
	}

	public TextMessage gettMsg() {
		return tMsg;
	}

	public void settMsg(TextMessage tMsg) {
		this.tMsg = tMsg;
	}
}