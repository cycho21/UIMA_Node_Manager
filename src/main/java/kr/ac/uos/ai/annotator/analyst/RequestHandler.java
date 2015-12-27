package kr.ac.uos.ai.annotator.analyst;

import kr.ac.uos.ai.annotator.activemq.Sender;
import kr.ac.uos.ai.annotator.bean.protocol.Job;
import kr.ac.uos.ai.annotator.bean.protocol.MsgType;
import kr.ac.uos.ai.annotator.bean.protocol.Protocol;
import kr.ac.uos.ai.annotator.classloader.AnnotatorDynamicLoader;
import kr.ac.uos.ai.annotator.classloader.JobTracker;
import kr.ac.uos.ai.annotator.forker.ProcessForker;
import kr.ac.uos.ai.annotator.monitor.JobList;
import kr.ac.uos.ai.annotator.taskarchiver.TaskUnpacker;
import org.apache.commons.exec.ExecuteWatchdog;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import java.util.HashMap;

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
    private JobTracker jobTracker;
    private Sender nsdr;
    private ProcessForker processForker;

    public RequestHandler() {
        jobList = JobList.getInstance();
        annotatorDynamicLoader = new AnnotatorDynamicLoader();
    }

    public void init() {
        taskUnpacker = new TaskUnpacker();
        jobTracker = new JobTracker();
        processForker = new ProcessForker();
    }

    public Job makeJob(Message message) {
        Job job = new Job();
        try {
            job.setModifiedDate(message.getObjectProperty("modifiedDate").toString());
            job.setDeveloper(message.getObjectProperty("developer").toString());
            job.setJobName(message.getObjectProperty("jobName").toString());
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

    public void requestJob(Message message) {
//        Protocol protocol = makeProtocol(message);
        System.out.println("Annotator Running...");
        ExecuteWatchdog watchdog = processForker.forkNewProc();
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
            String path;
            if (tMsg.getObjectProperty("type").equals("jar")) {
                path = System.getProperty("user.dir") + "\\annotator\\";
            } else {
                path = System.getProperty("user.dir") + "\\inputFile\\";
            }
            String fullPath = path + tMsg.getObjectProperty("fileName");
            taskUnpacker.makeFileFromByteArray(path, fullPath, bytes);
            sdr.sendMessage("uploadSeq", "completed");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public HashMap getJobList() {
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

    public void setSdr(Sender sdr, Sender nsdr) {
        this.sdr = sdr;
        this.nsdr = nsdr;
    }

    public void test() {
        sdr.sendMessage("uploadSeq", "completed");
        annotatorDynamicLoader.loadClass("Test.jar", "kr.ac.uos.ai.Test", "Test1");
    }
}
