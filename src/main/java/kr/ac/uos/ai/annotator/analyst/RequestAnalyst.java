package kr.ac.uos.ai.annotator.analyst;

import kr.ac.uos.ai.annotator.activemq.Sender;
import kr.ac.uos.ai.annotator.bean.protocol.MsgType;
import kr.ac.uos.ai.annotator.monitor.AnnotatorRunningInfo;

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

    public void setSender(Sender sdr) {
        this.sdr = sdr;
        requestHandler.setSdr(sdr);
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
                try {
                    requestHandler.annoFirstRun(message.getObjectProperty("msgTxt").toString());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
                break;
            case GETANNOLIST:
                requestHandler.getAnnoList();
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
            default:
                /* doNothing */
                break;
        }
    }
}
