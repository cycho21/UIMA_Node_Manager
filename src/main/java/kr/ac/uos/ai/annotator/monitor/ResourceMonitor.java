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

    public ResourceMonitor() {
        System.out.println("Resource Monitor loaded");
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

    @Test
    public void test() {
        init();
        try {
            Mem mem = sigar.getMem();
            System.out.println(mem.getActualFree());
        } catch (SigarException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        /**
         * declare a constructor for CPUINFO
         *
         * Remember that a constructor normally is meant to set the class up...in
         * this case we are bending the rules just to make the example clearer.
         * Really the constructor should be empty and we should be doing this in a
         * method.
         */
// declare the Sigar class (we need this because it gathers the
// statistics)
            Sigar sigar = new Sigar();

// the output string is just going to hold our output string
            String output = "";

// an array of CPU Info classes. The CPUInfo class is a blank data
// holder class. Just like string it can hold any data.
            CpuInfo[] cpuInfoList = null;
// the try catch block means that if we get an error we are notified
            try {
// get the CPU information from the sigar library
                cpuInfoList = sigar.getCpuInfoList();
                System.out.println("This is length : " + cpuInfoList.length);
// if something foes wrong
            } catch (SigarException e) {
// write a description of the problem to the output
                e.printStackTrace();
// exit the constructor
                return;
            }

// for each item in the cpu info array
            for (CpuInfo info : cpuInfoList) {
// add the data to the output ( output += "something" means add
// "something" to the end of output)
                output += "\nCPU\n";
                output += "Vendor: " + info.getVendor() + "\n";
                output += "Clock: " + info.getMhz() + "Mhz\n";
            }

// finally, print the data to the output
            System.out.println(output);
        }
}
