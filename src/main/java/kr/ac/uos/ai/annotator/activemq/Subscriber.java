package kr.ac.uos.ai.annotator.activemq;

import kr.ac.uos.ai.annotator.analyst.RequestAnalyst;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.net.*;
import java.util.Enumeration;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - SnapShot
 *          on 2015-12-23 enemy
 * @link http://ai.uos.ac.kr:9000/lovebube/UIMA_Management_Client
 */

public class Subscriber implements Runnable {

    private String hostAddr;
    private String serverIP;
    private ActiveMQConnectionFactory connectionFactory;
    private Connection connection;
    private String clientID;
    private String topicName;
    private Session session;
    private MessageConsumer consumer;
    private RequestAnalyst requestAnalyst;
    private Message message;
    private Topic topic;
    private TopicSubscriber subscriber;

    public Subscriber(String topicName, String serverIP, String clientID) {
        this.topicName = topicName;
        this.serverIP = serverIP;
        this.clientID = clientID;
        this.hostAddr = "";
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

    public void consume() {
        try {
            message = subscriber.receive();
            System.out.println(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(true){
            consume();
            requestAnalyst.analysis(message);
        }
    }

    public void init() {
        connectionFactory = new ActiveMQConnectionFactory("tcp://211.109.9.71:61616");
        try {
            connection = connectionFactory.createConnection();
            connection.setClientID(clientID);
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            topic = session.createTopic(topicName);
            subscriber = session.createDurableSubscriber(topic, hostAddr);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public void setRequestAnalyst(RequestAnalyst requestAnalyst) {
        this.requestAnalyst = requestAnalyst;
    }
}
