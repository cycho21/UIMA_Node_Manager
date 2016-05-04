package kr.ac.uos.ai.annotator.monitor;
import org.apache.commons.exec.ExecuteWatchdog;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - SnapShot
 *          on 2015-12-29 enemy
 * @link http://ai.uos.ac.kr:9000/lovebube/UIMA_Management_Client
 */

public class AnnotatorRunningInfo {
    private static AnnotatorRunningInfo ourInstance = new AnnotatorRunningInfo();
    private static HashMap<String, ExecuteWatchdog> annotatorList;

    public AnnotatorRunningInfo() {
    }

    public static HashMap<String, ExecuteWatchdog> getAnnotatorList() {
        if(annotatorList!=null){
            /* doNothing; */
        } else {
            annotatorList = new HashMap<>();
        }
        return annotatorList;
    }

    public static AnnotatorRunningInfo getInstance() {
        return ourInstance;
    }

}
