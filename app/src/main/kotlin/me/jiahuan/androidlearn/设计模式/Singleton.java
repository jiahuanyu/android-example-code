package me.jiahuan.androidlearn.设计模式;


import java.util.HashMap;

public class Singleton {


    private static class SingletonInner {
        private static Singleton instance = new Singleton();
    }

    private Singleton() {

    }


    public static Singleton getInstance() {
        return SingletonInner.instance;
    }

}
