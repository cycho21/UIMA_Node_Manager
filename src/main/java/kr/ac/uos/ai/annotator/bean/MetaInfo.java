/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - SnapShot
 * @see http://github.com/lovebube
 */

package kr.ac.uos.ai.annotator.bean;

import kr.ac.uos.ai.annotator.bean.protocol.MsgType;

public class MetaInfo {

    private String jobName;
    private String jobSize;
    private String version;
    private String modifiedDate;
    private String developer;

    public MetaInfo(MsgType msg, String jobName, String jobSize, String version, String modifiedDate, String developer) {
        typeAnalysis(msg.toString());
    }

    private void typeAnalysis(String msgType) {
        switch (msgType) {
            case "upload":
                makeUploadMeta();
                break;
            case "requestJob":
                makeRequestJobMeta();
                break;
            case "sendJob":
                makeSendJobMeta();
                break;
            case "jobList":
                makeJobListMeta();
                break;
            default:
                /* doNothing(); */
                break;
        }
    }

    private void makeJobListMeta() {

    }

    private void makeSendJobMeta() {

    }

    private void makeRequestJobMeta() {

    }

    private void makeUploadMeta() {

    }
}