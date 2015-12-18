package kr.ac.uos.ai.annotator.taskarchiver;

import org.junit.Test;

/**
 * This class is written by Chan Yeon, Cho
 * AI-Laboratory, Seoul, Korea
 * 2015. 9. 14.
 */

public class TaskArchiverCore {

    private static TaskPacker taskPacker;
    private static TaskUnpacker taskUnPacker;

    public TaskArchiverCore() {
        init();
    }

    public void init() {
        taskPacker = new TaskPacker();
        taskUnPacker = new TaskUnpacker();
    }

    public TaskPacker getPacker() {
        return taskPacker;
    }

    public static TaskUnpacker getTaskUnPacker() {
        return taskUnPacker;
    }


    /*
     *	This is example for task packing.
     */
    @Test
    public void Test() {
        TaskPacker taskPacker = new TaskPacker();
        taskPacker.addTaskElement("kr/ac/uos/ai/annotator/activemq/Sender.class");
        taskPacker.addTaskElement("kr/ac/uos/ai/annotator/activemq/ActiveMQManager.class");
        taskPacker.packTask("F:/jartest/newJar33.jar");
    }
}
