package test;

import kr.ac.uos.ai.annotator.activemq.ActiveMQManager;
import kr.ac.uos.ai.annotator.activemq.Receiver;
import kr.ac.uos.ai.annotator.activemq.Sender;
import org.junit.Test;

/**
 * Hello, Node!
 */

public class ApplicationTest {

    private ActiveMQManager activemqManager;
    private Receiver rcvr;
    private Sender sdr;

    public ApplicationTest() {
        init();
        startApp();
    }

    private void init() {
        activemqManager = new ActiveMQManager();
        activemqManager.init("testQueue");          // This init method makes receiver and starts receiver
        sdr = new Sender();
    }

    private void startApp() {

    }

    public static void main(String[] args) {
        System.out.println(1);
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
