package kr.ac.uos.ai.annotator.activemq;

import kr.ac.uos.ai.annotator.analyst.RequestAnalyst;

public class ActiveMQManager {

	private String queueName;
	private Receiver receiver;
	private RequestAnalyst requestAnalyst;
	private String serverIP;
	private Sender sender;
	private Subscriber subscriber;

	public ActiveMQManager() {
	}

	public void init(String queueName) {
		this.queueName = queueName;
		requestAnalyst = new RequestAnalyst();
		requestAnalyst.init();
		requestAnalyst.setSender(sender);

		subscriber = new Subscriber("basicTopicName", serverIP, System.getProperty("user.name" + 4));
        subscriber.setRequestAnalyst(requestAnalyst);
		subscriber.init();
		Thread subscribeThread = new Thread(subscriber);
		subscribeThread.start();

		receiver = new Receiver();
		receiver.setServerIP(serverIP);
		receiver.setQueueName(queueName);
		receiver.setRequestAnalyst(requestAnalyst);
		receiver.setSender(sender);
		receiver.init();
		Thread receiverThread = new Thread(receiver);
		receiverThread.start();
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	public void setSender(Sender sender) {
		this.sender = sender;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
}
