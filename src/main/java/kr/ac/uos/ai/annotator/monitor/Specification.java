package kr.ac.uos.ai.annotator.monitor;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - SnapShot
 *          on 2016-04-14 enemy
 * @link http://ai.uos.ac.kr:9000/lovebube/UIMA_Management_Client
 */

public class Specification {

    private String cpuCore;
    private String cpuClock;
    private String memory;

    public Specification() {
    }

    public String getCpuCore() {
        return cpuCore;
    }

    public void setCpuCore(String cpuCore) {
        this.cpuCore = cpuCore;
    }

    public String getCpuClock() {
        return cpuClock;
    }

    public void setCpuClock(String cpuClock) {
        this.cpuClock = cpuClock;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }
}