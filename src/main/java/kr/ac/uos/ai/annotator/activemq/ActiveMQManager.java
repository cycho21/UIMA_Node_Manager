package kr.ac.uos.ai.annotator.activemq;

import kr.ac.uos.ai.annotator.analyst.RequestAnalyst;

public class ActiveMQManager {

	private String queueName;
	private ClientReceiver clientReceiver;
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

		subscriber = new Subscriber("basicTopicName", serverIP, System.getProperty("user.name"));
        subscriber.setRequestAnalyst(requestAnalyst);
		subscriber.init();
		Thread subscribeThread = new Thread(subscriber);
		subscribeThread.start();

		clientReceiver = new ClientReceiver();
		clientReceiver.setServerIP(serverIP);
		clientReceiver.setQueueName(queueName);
		clientReceiver.setRequestAnalyst(requestAnalyst);
		clientReceiver.setSender(sender);
		clientReceiver.init();
		Thread receiverThread = new Thread(clientReceiver);
		receiverThread.start();
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public ClientReceiver getClientReceiver() {
		return clientReceiver;
	}

	public void setClientReceiver(ClientReceiver clientReceiver) {
		this.clientReceiver = clientReceiver;
	}

	public void setSender(Sender sender) {
		this.sender = sender;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
}
