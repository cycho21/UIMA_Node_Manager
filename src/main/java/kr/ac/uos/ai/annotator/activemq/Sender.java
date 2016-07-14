package kr.ac.uos.ai.annotator.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.net.*;
import java.util.Enumeration;

public class Sender {
    private String hostAddr;
    private ActiveMQConnectionFactory factory;
	private Connection connection;
	private Session session;
	private Queue queue;
	private MessageProducer producer;
	private String serverIP;

	public Sender() {
        hostAddr = "";
        try {
            Enumeration<NetworkInterface> nienum = NetworkInterface.getNetworkInterfaces();
            while (nienum.hasMoreElements()) {
                NetworkInterface ni = nienum.nextElement();
                Enumeration<InetAddress> kk= ni.getInetAddresses();
                while (kk.hasMoreElements()) {
                    InetAddress inetAddress = kk.nextElement();
                    if (!inetAddress.isLoopbackAddress() &&
                            !inetAddress.isLinkLocalAddress() &&
                            inetAddress.isSiteLocalAddress()) {
                        hostAddr = inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
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
        if (serverIP == null) {
        } else {
            factory = new ActiveMQConnectionFactory("tcp://211.109.9.71:61616");
            try {
                connection = factory.createConnection();
                connection.start();
                session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            } catch (JMSException e) {
                e.printStackTrace();
            }
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

	public void sendMessage(String simpleMsgType, String process) {
		TextMessage message;
		try {
            message = session.createTextMessage();
			message.setObjectProperty("msgType", simpleMsgType);
			message.setObjectProperty("text", process);
            message.setObjectProperty("ip", hostAddr);
			producer.send(message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
    }

	public void sendAnnoInfo(String annotatorName) {
		TextMessage message;
		try {
			message = session.createTextMessage();
			message.setObjectProperty("msgType", "annoInfo");
			message.setObjectProperty("annotatorName", annotatorName);
			message.setObjectProperty("ip", hostAddr);
            producer.send(message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public void sendResource(String memory, String freeCPU) {
		TextMessage message;
		try {
			message = session.createTextMessage();
			message.setObjectProperty("msgType", "resource");
			message.setObjectProperty("ip", hostAddr);
			message.setObjectProperty("freeMemoryPerc", memory);
            message.setObjectProperty("freeCPUPerc", freeCPU);
            producer.send(message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}