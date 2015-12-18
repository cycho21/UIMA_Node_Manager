package kr.ac.uos.ai.annotator.classloader;

import org.junit.Test;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - SnapShot
 *          on 2015-12-18 enemy
 * @link http://ai.uos.ac.kr:9000/lovebube/UIMA_Framework_Plugin
 */

public class AnnotatorDynamicLoader {

    public AnnotatorDynamicLoader() {
    }

    @Test
    public void loadClass() {
        try {
            Class cl = Class.forName("kr.ac.uos.ai.annotator.monitor.ResourceMonitor");
            Object obj = cl.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
