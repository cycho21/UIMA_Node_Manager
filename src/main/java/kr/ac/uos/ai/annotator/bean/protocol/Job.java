package kr.ac.uos.ai.annotator.bean.protocol;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - SnapShot
 *          on 2015-12-15 enemy
 */

public class Job {

    private String jobName;
    private String version;
    private String modifiedDate;
    private String developer;
    private String jobSize;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public void setJobSize(String jobSize) {
        this.jobSize = jobSize;
    }

    public String getJobSize() {
        return jobSize;
    }
}
