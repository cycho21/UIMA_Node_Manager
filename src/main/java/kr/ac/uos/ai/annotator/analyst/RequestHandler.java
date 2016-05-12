package kr.ac.uos.ai.annotator.analyst;

import kr.ac.uos.ai.annotator.activemq.Sender;
import kr.ac.uos.ai.annotator.bean.protocol.Job;
import kr.ac.uos.ai.annotator.bean.protocol.MsgType;
import kr.ac.uos.ai.annotator.bean.protocol.Protocol;
import kr.ac.uos.ai.annotator.classloader.AnnotatorDynamicLoader;
import kr.ac.uos.ai.annotator.classloader.JobTracker;
import kr.ac.uos.ai.annotator.forker.ProcessForker;
import kr.ac.uos.ai.annotator.monitor.AnnotatorRunningInfo;
import kr.ac.uos.ai.annotator.monitor.JobList;
import kr.ac.uos.ai.annotator.taskarchiver.TaskUnpacker;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - Snapshot
 *          on 2015-12-20
 * @link http://github.com/lovebube
 */
public class RequestHandler {

    private String hostAddr;
    private JobList jobList;
    private AnnotatorDynamicLoader annotatorDynamicLoader;
    private TaskUnpacker taskUnpacker;
    private Sender sdr;
    private JobTracker jobTracker;
    private ProcessForker processForker;
    private Boolean annoIsRun;
    private AnnotatorRunningInfo annotatorList;

    public RequestHandler() {
        jobList = JobList.getInstance();
        annotatorDynamicLoader = new AnnotatorDynamicLoader();


        /*

         */
        Enumeration<NetworkInterface> nienum = null;
        try {
            nienum = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        while (nienum.hasMoreElements()) {

            NetworkInterface ni = nienum.nextElement();
            Enumeration<InetAddress> kk= ni.getInetAddresses();

            while (kk.hasMoreElements()) {

                InetAddress inetAddress = kk.nextElement();

                if (!inetAddress.isLoopbackAddress() &&

                        !inetAddress.isLinkLocalAddress() &&

                        inetAddress.isSiteLocalAddress()) {

                    hostAddr = inetAddress.getHostAddress().toString();

                }
            }
        }

        /*

         */

    }

    public void init() {
        taskUnpacker = new TaskUnpacker();
        jobTracker = new JobTracker();
        processForker = new ProcessForker();
        annotatorList = new AnnotatorRunningInfo();
        this.annoIsRun = false;
    }

    public Job makeJob(Message message) {
        Job job = new Job();
        try {
            job.setAnnoName(message.getObjectProperty("fileName").toString());
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return job;
    }

    public Protocol makeProtocol(Message message) {
        Protocol protocol = new Protocol();
        protocol.setJob(makeJob(message));
        try {
            protocol.setMsgType(MsgType.valueOf(message.getObjectProperty("msgType").toString().toUpperCase()));
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return protocol;
    }

    public void upLoad(Message message) {

        try {

            BytesMessage tMsg = (BytesMessage) message;

            if(tMsg.getObjectProperty("connectCallBack").equals("false")) {
                byte[] bytes = new byte[(int) tMsg.getBodyLength()];
                tMsg.readBytes(bytes);
                makeFile(bytes, tMsg);
            } else {
                if(tMsg.getObjectProperty("connectCallBack").equals(hostAddr)){
                    byte[] bytes = new byte[(int) tMsg.getBodyLength()];
                    tMsg.readBytes(bytes);
                    makeFile(bytes, tMsg);
                } else {
                    // doNothing;
                }
            }

        } catch (JMSException e) {

            e.printStackTrace();

        }
    }

    public void makeFile(byte[] bytes, BytesMessage tMsg) {
        try {
            String path;
            System.out.println(("This is user.dir " + System.getProperty("user.dir")));
            if (tMsg.getObjectProperty("type").equals("jar")) {
                path = System.getProperty("user.dir") + "/annotator/";
//                path = System.getProperty("user.dir") + "\\annotator\\";
            } else {
                path = System.getProperty("user.dir") + "/inputFile/";
//                path = System.getProperty("user.dir") + "\\inputFile\\";
            }
            String fullPath = path + tMsg.getObjectProperty("fileName");
            taskUnpacker.makeFileFromByteArray(path, fullPath, bytes);

            switch (tMsg.getObjectProperty("updateType").toString()){

                case "update" :
                    annoKill(tMsg.getObjectProperty("fileName").toString());
                    annoFirstRun(tMsg.getObjectProperty("fileName").toString());
                    break;

                case "enroll" :
                    annoFirstRun(tMsg.getObjectProperty("fileName").toString());
                    break;

                default:
                    break;
            }

            sdr.sendMessage("uploadSeq", "completed");

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void annoKill(String annoName) {
        AnnotatorRunningInfo.getAnnotatorList().get(annoName).destroyProcess();
        System.out.println(annoName + " killed...");
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

    public void setSdr(Sender sdr) {
        this.sdr = sdr;
    }

    public void annoRun(Message message) {
        Protocol protocol = makeProtocol(message);
        if(annoIsRun){
            System.out.println("Annotator is already running...");
        } else {
            System.out.println("Annotator is starting...");
            ProcessForker processForker = new ProcessForker();
            Thread tempThread = new Thread(processForker);
            processForker.setJarFileName(protocol.getJob().getAnnoName());
            System.out.println(protocol.getJob().getAnnoName());
            sdr.sendMessage("anno", "completed");
            tempThread.start();
        }
    }

    public void annoFirstRun(String annoName) {
        System.out.println("Annotator is starting...");

        ProcessForker processForker = new ProcessForker();
        Thread tempThread = new Thread(processForker);
        processForker.setJarFileName(annoName);
        tempThread.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(annoName + "    " + processForker.getWatchdog());
        AnnotatorRunningInfo.getAnnotatorList().put(annoName, processForker.getWatchdog());

        sdr.sendAnnoInfo(annoName);
    }

}
