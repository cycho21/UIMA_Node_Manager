package kr.ac.uos.ai.annotator.forker;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;

import java.io.IOException;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - Snapshot
 *          on 2015-12-27
 * @link http://github.com/lovebube
 */
public class ProcessForker implements Runnable {

    private String jarFileName;
    private DefaultExecutor executor;
    private ExecuteWatchdog watchdog;

    public String getJarFileName() {
        return jarFileName;
    }

    public void setJarFileName(String jarFileName) {
        this.jarFileName = jarFileName;
    }

    public ProcessForker() {
    }

    public void forkNewProc() {
        String path = System.getProperty("user.dir");
        String line = "java -jar " + path + "/annotator/" + jarFileName;
        CommandLine cmdLine = CommandLine.parse(line);
        watchdog = new ExecuteWatchdog(ExecuteWatchdog.INFINITE_TIMEOUT);
        System.out.println("This is WatchDog!! " + watchdog);

        executor = new DefaultExecutor();
        executor.setWatchdog(watchdog);

        try {

            executor.execute(cmdLine);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        forkNewProc();
    }

    public DefaultExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(DefaultExecutor executor) {
        this.executor = executor;
    }

    public ExecuteWatchdog getWatchdog() {
        return watchdog;
    }

    public void setWatchdog(ExecuteWatchdog watchdog) {
        this.watchdog = watchdog;
    }
}