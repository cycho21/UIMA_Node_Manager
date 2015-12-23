package kr.ac.uos.ai.annotator.activemq;

import kr.ac.uos.ai.annotator.analyst.RequestAnalyst;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.memory.list.MessageList;

import javax.jms.*;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - SnapShot
 *          on 2015-12-23 enemy
 * @link http://ai.uos.ac.kr:9000/lovebube/UIMA_Management_Client
 */

public class Subscriber implements Runnable {

    private String serverIP;
    private ActiveMQConnectionFactory connectionFactory;
    private Connection connection;
    private String clientID;
    private String topicName;
    private Session session;
    private MessageConsumer consumer;
    private RequestAnalyst requestAnalyst;
    private Message message;

    public Subscriber(String topicName, String serverIP, String clientID) {
        this.topicName = topicName;
        this.serverIP = serverIP;
        this.clientID = clientID;
    }

    public void consume() {
        try {
            message = consumer.receive();
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
        requestAnalyst = new RequestAnalyst();
        connectionFactory = new ActiveMQConnectionFactory("tcp://" + serverIP + ":61616");
        try {
            connection = connectionFactory.createConnection();
            connection.setClientID(clientID);
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(topicName);
            consumer = session.createConsumer(topic);
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

}
