package me.jiahuan.androidlearn.java_test.设计模式.单例模式;


public class Singleton {
    private Singleton() {
    }

    public static Singleton getInstance() {
        return SingletonHolder.sInstance;
    }

    private static class SingletonHolder {
        private static final Singleton sInstance = new Singleton();
    }
}
