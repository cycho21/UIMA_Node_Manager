package kr.ac.uos.ai.annotator.classloader;

import org.junit.Test;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - Snapshot
 *          on 2015-12-18 enemy
 * @link http://ai.uos.ac.kr:9000/lovebube/UIMA_Framework_Plugin
 */

public class AnnotatorDynamicLoader {

    private String jarPath;

    public AnnotatorDynamicLoader() {
    }

    @Test
    public void loadClass2() {
        this.jarPath = "Test.jar";
        String tempPath = jarPath;
        String path = System.getProperty("user.dir") + "\\lib\\" + tempPath;
        File file = new File(path);
        try {
            URL url = file.toURL();
            URL[] urls = new URL[]{url};
            ClassLoader loader = new URLClassLoader(urls);
//            Class<?> tempClass = loader.loadClass("kr.ac.uos.ai.Test");
//            Constructor<?> constructor = tempClass.getConstructor();
//            Object obj = constructor.newInstance();
//            Method method = tempClass.getMethod("Test1");
//            method.invoke(obj);

            Class<?> tempClass = Class.forName("kr.ac.uos.ai.Test", true, loader);
            Class<? extends Runnable> runClass = tempClass.asSubclass(Runnable.class);
            Constructor<? extends Runnable> constructor = runClass.getConstructor();
            Runnable doRun = constructor.newInstance();
            Method method = tempClass.getMethod("Test1");
            doRun.run();
            method.invoke(doRun);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }
}
