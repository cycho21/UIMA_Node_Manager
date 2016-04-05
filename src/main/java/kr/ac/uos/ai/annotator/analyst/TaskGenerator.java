package kr.ac.uos.ai.annotator.analyst;

import kr.ac.uos.ai.annotator.bean.Task;
import kr.ac.uos.ai.annotator.bean.protocol.MsgType;
import javax.jms.JMSException;
import javax.jms.Message;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - Snapshot
 *          on 2015-12-20
 * @link http://github.com/lovebube
 */

public class TaskGenerator {

    public TaskGenerator() {
    }

    public Task genTask(Message message) {
        try {
            String  author  = message.getObjectProperty("AUTHOR").toString();
            String  time    = message.getObjectProperty("TIME").toString();
            MsgType msgType = MsgType.valueOf(message.getObjectProperty("msgType").toString().toUpperCase());
            Task task = new Task(author, msgType, time);
            return task;
        } catch (JMSException e) {
            e.printStackTrace();
            return null;
        }
    }
}
