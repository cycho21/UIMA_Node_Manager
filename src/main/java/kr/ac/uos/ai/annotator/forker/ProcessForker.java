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

    private ExecuteWatchdog watcher;
    private String jarFileName;

    public ExecuteWatchdog getWatcher() {
        return watcher;
    }

    public void setWatcher(ExecuteWatchdog watcher) {
        this.watcher = watcher;
    }

    public String getJarFileName() {
        return jarFileName;
    }

    public void setJarFileName(String jarFileName) {
        this.jarFileName = jarFileName;
    }

    public ProcessForker() {
    }

    public ExecuteWatchdog forkNewProc() {
        String path = System.getProperty("user.dir");
        String line = "java -jar " + path +
                "/annotator/" + jarFileName;
        CommandLine cmdLine = CommandLine.parse(line);
        DefaultExecutor executor = new DefaultExecutor();
        System.out.println(line);
        try {
            int exitValue = executor.execute(cmdLine);
            watcher = executor.getWatchdog();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return watcher;
    }

    @Override
    public void run() {
        forkNewProc();
    }
}