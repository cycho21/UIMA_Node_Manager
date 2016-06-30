package kr.ac.uos.ai.annotator.monitor;

import org.hyperic.sigar.Cpu;

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
    private String freeMemeory;
    private String freeCPU;

    public Specification() {
    }

    public String getFreeMemeory() {
        return freeMemeory;
    }

    public void setFreeMemeory(String freeMemeory) {
        this.freeMemeory = freeMemeory;
    }

    public String getFreeCPU() {
        return freeCPU;
    }

    public void setFreeCPU(String freeCPU) {
        this.freeCPU = freeCPU;
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