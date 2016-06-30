package kr.ac.uos.ai.annotator.monitor;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.junit.Test;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - SnapShot
 *          on 2015-11-03
 */

public class ResourceMonitor {

    private static Memory memory;
    private static CPU cpu;
    public static Sigar sigar;

    public ResourceMonitor(){
    }

    public void init() {

        if (memory == null) {
            memory = new Memory();
        }
        if (cpu == null) {
            cpu = new CPU();
        }
        if (sigar == null) {
            sigar = new Sigar();
        }

    }

    public Specification getSpec() {
        Specification spec = new Specification();
        try {

            spec.setCpuCore(String.valueOf(sigar.getCpuInfoList().length));
            spec.setMemory(String.valueOf(sigar.getMem().getFreePercent()));
            spec.setFreeCPU(String.valueOf(sigar.getCpuPerc().getIdle()*100));

        } catch (SigarException e) {
            e.printStackTrace();
        }
        return spec;
    }

    public String getMem() {
        Mem mem = null;
        try {
            mem = sigar.getMem();
        } catch (SigarException e) {
            e.printStackTrace();
        }
        return String.valueOf(mem.getTotal()/1024);
    }

    public CpuInfo[] getCPU() {
        CpuInfo[] cpuInfoList = null;
        try {
            cpuInfoList = sigar.getCpuInfoList();
        } catch (SigarException e) {
            e.printStackTrace();
        }
        return cpuInfoList;
    }

    @Test
    public void test() {
        init();
//        try {
//            Mem mem = sigar.getMem();
//            System.out.println((mem.getTotal()/1024)/1024 + "MB");
//        } catch (SigarException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void test2() {
            Sigar sigar = new Sigar();

            String output = "";

            CpuInfo[] cpuInfoList = null;

            try {
                cpuInfoList = sigar.getCpuInfoList();
                System.out.println("CPU : " + cpuInfoList.length + " Core");
            } catch (SigarException e) {
                e.printStackTrace();
                return;
            }

            for (CpuInfo info : cpuInfoList) {
                output += "\nCPU\n";
                output += "Vendor: " + info.getVendor() + "\n";
                output += "Clock: " + info.getMhz() + "Mhz\n";
            }

            System.out.println(output);
        }


}