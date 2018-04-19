package me.jiahuan.androidlearn.java_test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by vendor on 2018/4/19.
 * 自定义 ClassLoder
 */


public class DiskClassLoader extends ClassLoader {
    private String path;

    public DiskClassLoader(String path) {
        this.path = path;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class clazz = null;
        byte[] classData = loadClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            clazz = defineClass(name, classData, 0, classData.length);
        }
        return clazz;
    }

    private byte[] loadClassData(String name) {
        String fileName = getFileName(name);
        File file = new File(this.path, fileName);
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            inputStream = new FileInputStream(file);
            byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String getFileName(String name) {
        int index = name.lastIndexOf('.');
        if (index == -1) {//如果没有找到'.'则直接在末尾添加.class
            return name + ".class";
        } else {
            return name.substring(index + 1) + ".class";
        }
    }
}
