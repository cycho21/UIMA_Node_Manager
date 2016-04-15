package kr.ac.uos.ai.annotator;

import kr.ac.uos.ai.annotator.activemq.ActiveMQManager;
import kr.ac.uos.ai.annotator.activemq.Sender;
import kr.ac.uos.ai.annotator.monitor.ResourceMonitor;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Hello, Node!
 */

public class Application {

    private ActiveMQManager activemqManager;
    private Sender sdr;
    private String serverIP;

    public Application() {
        ResourceMonitor resourceMonitor = new ResourceMonitor();
        resourceMonitor.init();
        resourceMonitor.test2();
//        init();
//        startApp();
    }

    private void init() {
        this.serverIP = "211.109.9.71";
        getIP();
        activemqManager = new ActiveMQManager();
        activemqManager.setServerIP(serverIP);

        sdr = new Sender();
        sdr.setServerIP(serverIP);
        sdr.init();
        sdr.createQueue("main");
        sdr.sendMessage("connected", getIP());

        activemqManager.setSender(sdr);
        activemqManager.init("node");          // This init method makes receiver and starts receiver

        System.out.println("Node_Manager initializing OK");
    }

    private String getIP() {
        String hostAddr = "";
        try {
            Enumeration<NetworkInterface> nienum = NetworkInterface.getNetworkInterfaces();
            while (nienum.hasMoreElements()) {
                NetworkInterface ni = nienum.nextElement();
                Enumeration<InetAddress> kk = ni.getInetAddresses();
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
        return hostAddr;
    }

    private void startApp() {
    }

    public static void main(String[] args) {
        new Application();
    }
}
