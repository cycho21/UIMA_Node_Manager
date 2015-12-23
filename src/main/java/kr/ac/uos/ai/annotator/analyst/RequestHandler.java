package kr.ac.uos.ai.annotator.analyst;

import kr.ac.uos.ai.annotator.activemq.Sender;
import kr.ac.uos.ai.annotator.bean.protocol.Job;
import kr.ac.uos.ai.annotator.bean.protocol.MsgType;
import kr.ac.uos.ai.annotator.bean.protocol.Protocol;
import kr.ac.uos.ai.annotator.classloader.AnnotatorDynamicLoader;
import kr.ac.uos.ai.annotator.monitor.JobList;
import kr.ac.uos.ai.annotator.taskarchiver.TaskUnpacker;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import java.util.ArrayList;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - Snapshot
 *          on 2015-12-20
 * @link http://github.com/lovebube
 */
public class RequestHandler {

    private JobList jobList;
    private AnnotatorDynamicLoader annotatorDynamicLoader;
    private TaskUnpacker taskUnpacker;
    private Sender sdr;

    public RequestHandler() {
        jobList = JobList.getInstance();
        annotatorDynamicLoader = new AnnotatorDynamicLoader();
    }

    public void init() {
        taskUnpacker = new TaskUnpacker();
    }

    public Job makeJob(Message message) {
        Job job = new Job();
        try {
            job.setModifiedDate(message.getObjectProperty("modifiedDate").toString());
            job.setDeveloper(message.getObjectProperty("developerName").toString());
            job.setJobName(message.getObjectProperty("jobName").toString());
            job.setJobSize(message.getObjectProperty("jobSize").toString());
            job.setVersion(message.getObjectProperty("version").toString());
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return job;
    }

    public Protocol makeProtocol(Message message) {
        Protocol protocol = new Protocol();
        protocol.setJob(makeJob(message));
        try {
            protocol.setMsgType(MsgType.valueOf(message.getObjectProperty("msgType").toString()));
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return protocol;
    }

    public void sendJob(Message message) {
        JobList.getJobList().add(makeJob(message));
    }

    public void upLoad(Message message) {
        try {
            BytesMessage tMsg = (BytesMessage) message;
            byte[] bytes = new byte[(int) tMsg.getBodyLength()];
            tMsg.readBytes(bytes);
            makeFile(bytes, tMsg);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void makeFile(byte[] bytes, BytesMessage tMsg) {
        try {
            taskUnpacker.makeFileFromByteArray(System.getProperty("user.dir") + "\\lib\\" +
                    tMsg.getObjectProperty("FileName"), bytes);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void requestJob(Message message) {
        Protocol protocol = makeProtocol(message);

    }

    public ArrayList<Job> getJobList() {
        return jobList.getJobList();
    }


    public void uploadSeq(Message message) {
        BytesMessage tMsg = (BytesMessage) message;
        try {
            byte[] bytes = new byte[(int) tMsg.getBodyLength()];
            tMsg.readBytes(bytes);
            for (int i = 0; i <= bytes.length; i++) {
                if (i == bytes.length) {
                    sdr.sendMessage("uploadSeq", "completed");
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void setSdr(Sender sdr) {
        this.sdr = sdr;
    }

    public void test() {
        sdr.sendMessage("uploadSeq", "completed");
        annotatorDynamicLoader.loadClass("Test.jar", "kr.ac.uos.ai.Test");
    }
}
