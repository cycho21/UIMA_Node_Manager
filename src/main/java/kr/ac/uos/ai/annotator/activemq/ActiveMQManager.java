package kr.ac.uos.ai.annotator.activemq;

import kr.ac.uos.ai.annotator.analyst.RequestAnalyst;

public class ActiveMQManager {

	private String mqueueName;
	private Receiver receiver;
	private RequestAnalyst requestAnalyst;
	private String serverIP;
	private Sender sender;

	public ActiveMQManager() {
	}

	public void init(String queueName) {
		this.mqueueName = queueName;
		requestAnalyst = new RequestAnalyst();
		requestAnalyst.init();
		receiver.setServerIP(serverIP);
		receiver.setQueueName(queueName);
		receiver.setRequestAnalyst(requestAnalyst);
		receiver.setSender(sender);
		receiver.init();
		Thread receiverThread = new Thread(receiver);
		receiverThread.start();
	}

	public String getMqueueName() {
		return mqueueName;
	}

	public void setMqueueName(String mqueueName) {
		this.mqueueName = mqueueName;
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
}
