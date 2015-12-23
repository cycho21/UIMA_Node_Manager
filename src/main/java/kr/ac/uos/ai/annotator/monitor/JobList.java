package kr.ac.uos.ai.annotator.monitor;

import kr.ac.uos.ai.annotator.bean.protocol.Job;
import java.util.ArrayList;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - SnapShot
 *          on 2015-12-23 enemy
 * @link http://ai.uos.ac.kr:9000/lovebube/UIMA_Management_Client
 */

public class JobList {
    private static JobList ourInstance = new JobList();
    private static ArrayList<Job> jobList;

    public static JobList getInstance() {
        return ourInstance;
    }

    public static ArrayList<Job> getJobList() {
        if(jobList!=null){
            /* doNothing; */
        } else {
            jobList = new ArrayList<Job>();
        }
        return jobList;
    }

    private JobList() {
    }
}
