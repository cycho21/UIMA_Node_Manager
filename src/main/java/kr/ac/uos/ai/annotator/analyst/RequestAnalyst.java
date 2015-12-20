package kr.ac.uos.ai.annotator.analyst;

import kr.ac.uos.ai.annotator.bean.Task;
import kr.ac.uos.ai.annotator.bean.protocol.MsgType;

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
        } else {
            /* doNothing */
        }
    }

    public void injectTask(Message message) {
        analysis(taskGenerator.genTask(message));
    }

    /**
     * @param task Task object that receiver receive.
     */
    public void analysis(Task task) {
        /* get msgType */
        String msgType = task.getMetaInfo().getPropertyMap().get("msgType").toUpperCase();
        switch (MsgType.valueOf(msgType)) {
            case REQUESTJOB:
                requestHandler.requestJob();
                break;
            case GETJOBLIST:
                requestHandler.getJobList();
                break;
            case UPLOAD:
                requestHandler.upLoad();
                break;
            case SENDJOB:
                requestHandler.sendJob();
                break;
            default:
                /* doNothing */
                break;
        }
    }
}
