package test;

import kr.ac.uos.ai.annotator.activemq.ActiveMQManager;
import kr.ac.uos.ai.annotator.activemq.Sender;
import kr.ac.uos.ai.annotator.taskarchiver.TaskArchiverCore;
import kr.ac.uos.ai.annotator.taskarchiver.TaskPacker;
import kr.ac.uos.ai.annotator.taskdistributor.TaskDistributor;
import kr.ac.uos.ai.annotator.taskdistributor.TaskDistributorCore;

/**
 * Hello, Node!
 */

public class ApplicationTest {

    private ActiveMQManager activemqManager;
    private Sender sdr;
    private TaskDistributor td;
    private TaskPacker tp;
    private String serverIP;

    public ApplicationTest() {
        init();
        startApp();
    }

    private void init() {
        this.serverIP = "localhost";
        TaskArchiverCore tac = new TaskArchiverCore();
        TaskDistributorCore tdc = new TaskDistributorCore();

        td = tdc.getTaskDistributor();
        tac.init();
        tdc.init();
        tp = tac.getPacker();
        tp.init();

        activemqManager = new ActiveMQManager();
        activemqManager.setServerIP(serverIP);

        sdr = new Sender();
        sdr.setServerIP(serverIP);
        sdr.init();
        sdr.createQueue("node2client");
        activemqManager.setSender(sdr);
        activemqManager.init("client2node");          // This init method makes receiver and starts receiver

    }

    private void startApp() {
    }

    public static void main(String[] args) {
        System.out.println(1);
        new ApplicationTest();
    }
}
