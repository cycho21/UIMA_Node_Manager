package kr.ac.uos.ai.annotator.bean.protocol;

import java.util.ArrayList;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - SnapShot
 *          on 2015-12-15 enemy
 */

public interface JobList {

    ArrayList<Job> jobList = null;
    int jobNum = 0;

    ArrayList<Job> getJobList();
    void setJobList(ArrayList<Job> jobList);

    int getJobNum();
    void setJobNum(int jobNum);
}
