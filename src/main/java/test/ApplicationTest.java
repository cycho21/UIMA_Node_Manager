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
        this.serverIP = "tcp://172.16.165.224";
        TaskArchiverCore tac = new TaskArchiverCore();
        TaskDistributorCore tdc = new TaskDistributorCore();

        td = tdc.getTaskDistributor();
        tac.init();
        tdc.init();
        tp = tac.getPacker();
        tp.init();

        activemqManager = new ActiveMQManager();

        sdr = new Sender();
        sdr.init();
        sdr.createQueue("testQueue2");
        activemqManager.setSender(sdr);
        activemqManager.init("testQueue2");          // This init method makes receiver and starts receiver

    }

    private void startApp() {
    }

    public static void main(String[] args) {
        System.out.println(1);
        new ApplicationTest();
    }
}
