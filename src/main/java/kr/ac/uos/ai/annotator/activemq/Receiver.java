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
	private Sender sender;
	private RequestAnalyst requestAnalyst;
	private String serverIP;

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
			message = consumer.receive();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			while (true) {
				consume();

				requestAnalyst.analysis(message);
				BytesMessage tMsg = (BytesMessage) message;
				byte[] bytes = new byte[(int) ((BytesMessage) message).getBodyLength()];
				tMsg.readBytes(bytes);

				makeAnnotatorFile(bytes);

				for (int i = 0; i <= bytes.length; i++) {
					System.out.println("uploadSeq " + i + "/" + bytes.length);
					sender.sendMessage("uploadSeq", +i + "/" + bytes.length);
					if (i == bytes.length) {
						sender.sendMessage("uploadSeq", "completed");
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Receiver Run Error");
		}
	}

	public void init() {
		requestAnalyst = new RequestAnalyst();
		taskUnpacker = new TaskUnpacker();
		factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		try {
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			consumer = session.createConsumer(queue);
			queue = session.createQueue(queueName);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}


	public void makeAnnotatorFile(byte[] bytes) {
		taskUnpacker.makeFileFromByteArray(System.getProperty("user.dir") + "\\lib\\", bytes);
	}

	public void makeInputFile(byte[] bytes) {
		taskUnpacker.makeFileFromByteArray(System.getProperty("user.dir") + "\\input\\", bytes);
	}

	public TextMessage gettMsg() {
		return tMsg;
	}

	public void settMsg(TextMessage tMsg) {
		this.tMsg = tMsg;
	}

	public void setSender(Sender sender) {
		this.sender = sender;
	}

	public void setRequestAnalyst(RequestAnalyst requestAnalyst) {
		this.requestAnalyst = requestAnalyst;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
}
