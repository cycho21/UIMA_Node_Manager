package kr.ac.uos.ai.annotator.activemq;

import kr.ac.uos.ai.annotator.analyst.RequestAnalyst;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class ActiveMQManager {

	private String queueName;
	private Receiver receiver;
	private RequestAnalyst requestAnalyst;
	private String serverIP;
	private Sender sender;
	private Subscriber subscriber;
    private Sender sdr;

    public ActiveMQManager() {
	}

	public void init(String queueName) {
		this.queueName = queueName;
        serverIP = "211.109.9.71";
		requestAnalyst = new RequestAnalyst();
		requestAnalyst.init();
		requestAnalyst.setSender(sdr);

		try {
			subscriber = new Subscriber("basicTopicName", serverIP, System.getProperty(Inet4Address.getLocalHost().getHostAddress().toString()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		subscriber.setRequestAnalyst(requestAnalyst);
		subscriber.init();
		Thread subscribeThread = new Thread(subscriber);
		subscribeThread.start();

		receiver = new Receiver();
		receiver.setServerIP(serverIP);
		receiver.setQueueName("node");
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

	public void setSender(Sender sdr) {
        this.sdr = sdr;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
}
