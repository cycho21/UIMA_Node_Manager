package test;

import kr.ac.uos.ai.annotator.activemq.ActiveMQManager;
import kr.ac.uos.ai.annotator.activemq.Receiver;
import kr.ac.uos.ai.annotator.activemq.Sender;
import kr.ac.uos.ai.annotator.taskarchiver.TaskArchiverCore;
import kr.ac.uos.ai.annotator.taskarchiver.TaskPacker;
import kr.ac.uos.ai.annotator.taskdistributor.TaskDistributor;
import kr.ac.uos.ai.annotator.taskdistributor.TaskDistributorCore;
import org.junit.Test;

/**
 * Hello, Node!
 */

public class ApplicationTest {

    private ActiveMQManager activemqManager;
    private Receiver rcvr;
    private Sender sdr;
    private TaskDistributor td;
    private TaskPacker tp;

    public ApplicationTest() {
        init();
        startApp();
    }

    private void init() {
        TaskArchiverCore tac = new TaskArchiverCore();
        TaskDistributorCore tdc = new TaskDistributorCore();

        td = tdc.getTaskDistributor();
        tac.init();
        tdc.init();
        tp = tac.getPacker();
//        tp.init();

        activemqManager = new ActiveMQManager();
        activemqManager.init("testQueue2");          // This init method makes receiver and starts receiver

        sdr = new Sender();
        sdr.init();
        sdr.createQueue("testQueue2");

        byte[] tempByte = tp.file2Byte("C:/library/TestAnnotator.jar");
        sdr.sendMessage(tempByte);
    }

    private void startApp() {

    }

    public static void main(String[] args) {
        System.out.println(1);
        new ApplicationTest();
//        String[] tempStringArray = args;
//
//        if (tempStringArray[0].equals("-")) {
//            caseStudyStart(tempStringArray);
//        }
    }

    private static void caseStudyStart(String[] temp) {
//        switch (temp[1]) {
//            case "o" :
//                break;
//            case "a" :
//                break;
//        }
    }

}
