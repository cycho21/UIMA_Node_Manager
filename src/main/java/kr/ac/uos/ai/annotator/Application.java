package kr.ac.uos.ai.annotator;

import kr.ac.uos.ai.annotator.activemq.ActiveMQManager;
import kr.ac.uos.ai.annotator.activemq.Sender;
import kr.ac.uos.ai.annotator.taskarchiver.TaskPacker;
import kr.ac.uos.ai.annotator.taskdistributor.TaskDistributor;

/**
 * Hello, Node!
 */

public class Application {

    private ActiveMQManager activemqManager;
    private Sender sdr;
    private Sender nsdr;
    private TaskDistributor td;
    private TaskPacker tp;
    private String serverIP;
    private boolean annoIsRun;

    public Application() {
        init();
        startApp();
    }

    private void init() {
        this.serverIP = "localhost";
        annoIsRun = false;
        activemqManager = new ActiveMQManager();
        activemqManager.setServerIP(serverIP);

        sdr = new Sender();
        sdr.setServerIP(serverIP);
        sdr.init();
        sdr.createQueue("node2client");

        nsdr = new Sender();
        nsdr.setServerIP(serverIP);
        nsdr.init();
        nsdr.createQueue("node2client");

        activemqManager.setSender(sdr, nsdr);
        activemqManager.init("main2node");          // This init method makes receiver and starts receiver

    }

    private void startApp() {
    }

    public static void main(String[] args) {
        new Application();
    }
}
