package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by wangteng on 2019/5/31.
 */
public class ClassUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static Class<?> loadClass(String className, boolean isInitialized) {
        try {
            return Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("load class failure", e);
            throw new RuntimeException(e);
        }
    }

    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        try {
            Enumeration<URL> enumeration = getClassLoader().getResources(packageName.replace(".", "/"));
            while (enumeration.hasMoreElements()) {
                URL url = enumeration.nextElement();
                String protocol = url.getProtocol();
                if("file".equals(protocol)) {
                    String packagePath = url.getPath().replace("%20", " ");
                    addClass(classSet, packagePath, packageName);
                } else if("jar".equals(protocol)) {
                    JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                    if(jarURLConnection != null) {
                        JarFile jarFile = jarURLConnection.getJarFile();
                        if(jarFile != null) {
                            Enumeration<JarEntry> jarEnties = jarFile.entries();
                            while(jarEnties.hasMoreElements()) {
                                JarEntry jarEntry = jarEnties.nextElement();
                                String jarEntryName = jarEntry.getName();
                                if(jarEntryName.endsWith(".class")) {
                                    String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                    doAddClass(classSet, className);
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("get Class set failure", e);
            throw new RuntimeException(e);
        }

        return classSet;
    }

    public static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isFile() && pathname.getName().endsWith(".class") || pathname.isDirectory();
            }
        });

        for(File file : files) {
            String fileName = file.getName();
            if(file.isFile()) {
                doAddClass(classSet, fileName);
            } else {
                if(! packagePath.endsWith("/")) {
                    packagePath += "/";
                }
                packagePath += fileName;
                packageName += "." + fileName;
                addClass(classSet, packagePath, packageName);
            }
        }
    }

    public static void doAddClass(Set<Class<?>> classSet, String className) {
        classSet.add(loadClass(className, false));
    }

}
