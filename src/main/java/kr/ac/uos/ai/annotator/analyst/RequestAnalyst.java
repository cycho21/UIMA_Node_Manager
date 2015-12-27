package kr.ac.uos.ai.annotator.analyst;

import kr.ac.uos.ai.annotator.activemq.Sender;
import kr.ac.uos.ai.annotator.bean.protocol.MsgType;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - Snapshot
 *          on 2015-12-20
 * @link http://github.com/lovebube
 */
public class RequestAnalyst {

    private TaskGenerator taskGenerator;
    private RequestHandler requestHandler;
    private Sender sdr;
    private Sender nsdr;

    /**
     * This constructor has no function.
     */
    public RequestAnalyst() {
    }

    public void init() {
        if(taskGenerator == null) {
            this.taskGenerator = new TaskGenerator();
        } else {
            /* doNothing */
        }

        if(requestHandler == null) {
            this.requestHandler = new RequestHandler();
            requestHandler.init();
        } else {
            /* doNothing */
        }
    }

    public void setSender(Sender sdr, Sender nsdr) {
        this.sdr = sdr;
        this.nsdr = nsdr;
        requestHandler.setSdr(sdr, nsdr);
    }

    /**
     * @param message message analysis
     */
    public void analysis(Message message) {

        /* get msgType */
        String msgType = null;
        try {
            msgType = message.getObjectProperty("msgType").toString().toUpperCase();
        } catch (JMSException e) {
            e.printStackTrace();
        }

        switch (MsgType.valueOf(msgType)) {
            case ANNORUN:
                requestHandler.annoRun(message);
                break;
            case REQUESTJOB:
                requestHandler.requestJob(message);
                break;
            case GETJOBLIST:
                requestHandler.getJobList();
                break;
            case UPLOADSEQ:
                requestHandler.uploadSeq(message);
                break;
            case UPLOAD:
                requestHandler.upLoad(message);
                break;
            case TEST:
                System.out.println(111);
                requestHandler.test();
                break;
            default:
                /* doNothing */
                break;
        }
    }
}
