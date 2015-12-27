package kr.ac.uos.ai.annotator.activemq;

import kr.ac.uos.ai.annotator.analyst.RequestAnalyst;

public class ActiveMQManager {

	private String queueName;
	private Receiver receiver;
	private RequestAnalyst requestAnalyst;
	private String serverIP;
	private Sender sender;
	private Subscriber subscriber;
    private Sender nsdr;

    public ActiveMQManager() {
	}

	public void init(String queueName) {
		this.queueName = queueName;
		requestAnalyst = new RequestAnalyst();
		requestAnalyst.init();
		requestAnalyst.setSender(sender, nsdr);

		subscriber = new Subscriber("basicTopicName", serverIP, System.getProperty("user.name"));
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

	public void setSender(Sender sender, Sender nsdr) {
		this.sender = sender;
        this.nsdr = nsdr;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
}
