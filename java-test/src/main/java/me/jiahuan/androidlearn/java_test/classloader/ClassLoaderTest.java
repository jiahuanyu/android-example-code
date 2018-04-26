package me.jiahuan.androidlearn.java_test.classloader;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassLoaderTest {

    public static void main(String args[]) {
        // BootStrap ClassLoader
        System.out.println(System.getProperty("sun.boot.class.path"));

        // Extension ClassLoader
        System.out.println(System.getProperty("java.ext.dirs"));

        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        while (classLoader != null) {
            System.out.println(classLoader);
            classLoader = classLoader.getParent();
        }


        DiskClassLoader diskClassLoader = new DiskClassLoader("/Users/vendor/Desktop");

        try {
            Class c = diskClassLoader.loadClass("com.example.Jobs");
            if (c != null) {
                Object obj = c.newInstance();
                System.out.println(obj.getClass().getClassLoader());
                Method method = c.getDeclaredMethod("say", null);
                method.invoke(obj, null);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
